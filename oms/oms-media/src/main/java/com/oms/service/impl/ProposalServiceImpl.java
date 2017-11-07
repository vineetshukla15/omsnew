package com.oms.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.OMSUser;

import com.oms.commons.utils.FileUtil;
import com.oms.commons.utils.OMSConstants;
import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Proposal;
import com.oms.model.ProposalAudit;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.ProposalStatus;
import com.oms.model.ProposalVersion;
import com.oms.model.specification.ProposalSpecification;
import com.oms.repository.OMSUserRepository;
import com.oms.repository.ProposalDocumentRepository;
import com.oms.repository.ProposalRepository;
import com.oms.repository.ProposalStatusRepository;
import com.oms.repository.ProposalVersionRepository;
import com.oms.service.FileUploaderService;
import com.oms.service.OMSOrderService;
import com.oms.service.ProposalService;
import com.oms.viewobjects.Document;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class ProposalServiceImpl implements ProposalService {

	final static Logger logger = Logger.getLogger(ProposalServiceImpl.class);

	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalStatusRepository proposalStatusRepository;

	@Autowired
	private OMSUserRepository userRepository;

	@Autowired
	private ProposalDocumentRepository proposalDocumentRepository;

	@Autowired
	private ProposalVersionRepository proposalVersionRepository;

	@Value("${proposal.document.upload-dir}")
	private String proposalDocUploadDir;

	private FileUploaderService fileUploader;

	@Autowired
	private OMSOrderService omsOrderService;

	@Override
	public List<Proposal> getAllProposals() {
		List<Proposal> proposalList = null;
		try {
			proposalList = proposalRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Proposal information", ex);
		}
		return proposalList;
	}

	@Override
	public Proposal getProposalById(Long id) throws OMSSystemException {
		try {
			Proposal proposal = proposalRepository.findByProposalId(id);
			Collections.sort(proposal.getAudits(), (p1, p2) -> p2.getEndDate().compareTo(p1.getEndDate()));
			return proposal;
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Proposal with id {" + id + "} does not exist.", ex);
		}

	}

	@Override
	@Transactional
	public Proposal addProposal(Proposal proposal, Long userId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		try {
			Set<ProposalVersion> listOfProposalVersions = new HashSet<ProposalVersion>();
			ProposalVersion proposalVersion = getProposalVersion(proposal);
			if (proposal.getVersions() != null && !proposal.getVersions().isEmpty()) {
				listOfProposalVersions = proposal.getVersions();
			} else {
				proposalVersion.setVersion(0L);
			}
			listOfProposalVersions.add(proposalVersion);
			proposal.setVersions(listOfProposalVersions);
			// proposal.getLineItems().forEach(li->li.getTarget().forEach(tr->tr.setId(0L)));
			/*
			 * if(proposal.getAssignTo()==null){
			 * proposal.setAssignTo(proposal.getMediaPlanner()); }
			 */
			proposal.setCreatedBy(userId);
			proposal.setUpdated(new Date());
			proposal.setCreated(new Date());
			proposal.setUpdatedBy(userId);
			Proposal savedProposal = proposalRepository.save(proposal);
			addProposalAudit(proposal, OMSConstants.NEW_PROPOSAL);
			return savedProposal;
		} catch (Exception e) {
			e.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Override
	public Proposal updateProposal(Proposal proposalDTO, Long userid)
			throws IllegalAccessException, InvocationTargetException {
		Proposal proposalDB = getProposalById(proposalDTO.getProposalId());
		try {
			if (proposalDB != null) {
				proposalDTO.setCreatedBy(proposalDB.getCreatedBy());
				proposalDTO.setCreated(proposalDB.getCreated());
				proposalDTO.setUpdated(new Date());
				proposalDTO.setUpdatedBy(userid);
				Set<ProposalVersion> updateVersions = new HashSet<ProposalVersion>();
				for (ProposalVersion pv : proposalDTO.getVersions()) {
					pv.setProposal(proposalDTO);
					if (pv.getPreviousStatus() == null
							|| pv.getStatus().getStatusId() != pv.getPreviousStatus().getStatusId()) {
						pv.setPreviousStatus(pv.getStatus());
					}
					updateVersions.add(pv);
				}
				proposalDTO.setVersions(updateVersions);
				proposalRepository.save(proposalDTO);
			} else {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
						"Proposal with id {" + proposalDTO.getProposalId() + "} does not exist.");
			}
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Unable to update proposal id : " + proposalDTO.getProposalId() + ex);
		}
		return proposalDTO;
	}

	@Override
	public void deleteProposal(Long proposalId) throws OMSSystemException {
		Proposal proposal = proposalRepository.findById(proposalId);
		if (proposal != null && Boolean.FALSE.equals(proposal.isDeleted())) {
			proposalRepository.softDelete(proposalId);
			this.deleteDocuments(proposal);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal with id {" + proposalId + "} does not exist or already deleted.");
		}
	}

	// Hard delete proposal documents
	private void deleteDocuments(Proposal proposal) {
		Collection<ProposalDocument> prolposalDocuments = proposal.getDocuments();
		prolposalDocuments.forEach(proposalDocument -> {
			proposalDocument.setProposal(proposal);
			this.fileUploader.delete(proposalDocument.toDocument());
		});
		this.proposalDocumentRepository.delete(prolposalDocuments);
	}

	@Override
	public Proposal copyProposal(Long proposalId, Boolean isVersion, Long userId)
			throws IllegalAccessException, InvocationTargetException {
		Proposal proposal = getProposalById(proposalId);
		if (BooleanUtils.isTrue(isVersion)) {
			return addProposalVersion(proposal, userId);
		} else {
			return copyProposal(proposal, userId);
		}
	}

	public Proposal copyProposal(Proposal proposal, Long userId)
			throws IllegalAccessException, InvocationTargetException {

		Proposal copiedProposal = new Proposal();
		copiedProposal.setAdvertiser(proposal.getAdvertiser());
		copiedProposal.setAdvertiserDiscount(proposal.getAdvertiserDiscount());
		copiedProposal.setAgency(proposal.getAgency());
		copiedProposal.setBillingSource(proposal.getBillingSource());
		copiedProposal.setBudget(proposal.getBudget());
		copiedProposal.setCurrency(proposal.getCurrency());
		copiedProposal.setDescription(proposal.getDescription());
		copiedProposal.setDueDate(proposal.getDueDate());
		copiedProposal.setEndDate(proposal.getEndDate());
		copiedProposal.setName(proposal.getName());
		copiedProposal.setNotes(proposal.getNotes());
		copiedProposal.setPercentageOfClose(proposal.getPercentageOfClose());
		copiedProposal.setPricingModel(proposal.getPricingModel());
		copiedProposal.setSalesCategory(proposal.getSalesCategory());
		copiedProposal.setSalesPerson(proposal.getSalesPerson());
		copiedProposal.setStartDate(proposal.getStartDate());
		copiedProposal.setTerms(proposal.getTerms());
		copiedProposal.setTrafficker(proposal.getTrafficker());
		copiedProposal.setOpportunity(proposal.getOpportunity());
		copiedProposal.setStatus(proposal.getStatus());
		copiedProposal.setSubmitted(false);
		copiedProposal.setLineItems(proposal.getLineItems());
		copiedProposal.setCreatedBy(proposal.getCreatedBy());
		copiedProposal.setUpdatedBy(proposal.getUpdatedBy());
		// TODO: Will revert it, done for testing
		copiedProposal.setAction(OMSConstants.NEW_PROPOSAL);
		return addProposal(copiedProposal, userId);
	}

	public Proposal addProposalVersion(Proposal proposal, Long userId)
			throws IllegalAccessException, InvocationTargetException {
		ProposalVersion proposalVersion = getProposalVersion(proposal);
		proposalVersion = proposalVersionRepository.save(proposalVersion);
		addProposalAudit(proposalVersion);
		Set<ProposalVersion> listOfProposalVersions = proposal.getVersions();
		listOfProposalVersions.add(proposalVersion);
		proposal.setVersions(listOfProposalVersions);
		addProposalAudit(proposal, OMSConstants.NEW_PROPOSAL_VERSION);
		return updateProposal(proposal, userId);
	}

	@Override
	public void addProposalAudit(ProposalVersion proposalVersion) {
		ProposalAudit proposalAudit = new ProposalAudit();
		proposalAudit.setAction(proposalVersion.getProposal().getAction());
		proposalAudit.setEndDate(new java.sql.Timestamp(new Date().getTime()));
		proposalAudit.setVersionId(proposalVersion.getSeqNo());
		proposalAudit.setStatus(proposalVersion.getStatus());
		OMSUser user = userRepository.findByUserId(proposalVersion.getProposal().getUpdatedBy());
		proposalAudit.setUserId(user);
		List<ProposalAudit> listOfProposalAudit = proposalVersion.getAudits();
		if (null == listOfProposalAudit) {
			listOfProposalAudit = new ArrayList<ProposalAudit>();
		}
		listOfProposalAudit.add(proposalAudit);
		proposalVersion.setAudits(listOfProposalAudit);
	}

	@Override
	public void addProposalAudit(Proposal proposal, String message) {
		if (proposal.getAction() != null || message != null) {
			ProposalAudit proposalAudit = new ProposalAudit();
			if (proposal.getAction() != null) {
				proposalAudit.setAction(proposal.getAction());
			} else {
				proposalAudit.setAction(message);
			}
			proposalAudit.setEndDate(new java.sql.Timestamp(new Date().getTime()));
			proposalAudit.setProposalId(proposal.getProposalId());
			proposalAudit.setStatus(proposal.getStatus());
			if (null != proposal.getUpdatedBy()) {
				OMSUser user = userRepository.findByUserId(proposal.getUpdatedBy());
				proposalAudit.setUserId(user);

			} else if (null != proposal.getCreatedBy()) {
				OMSUser user = userRepository.findByUserId(proposal.getUpdatedBy());
				proposalAudit.setUserId(user);
			}
			List<ProposalAudit> listOfProposalAudit = proposal.getAudits();
			if (null == listOfProposalAudit) {
				listOfProposalAudit = new ArrayList<ProposalAudit>();
			}
			listOfProposalAudit.add(proposalAudit);
			proposal.setAudits(listOfProposalAudit);
		}
	}

	@Override
	public Proposal addProposalAuditComments(Proposal proposal, String comments, Long userid, Long version) {
		ProposalAudit proposalAudit = new ProposalAudit();
		proposalAudit.setAction(comments);
		proposalAudit.setEndDate(new java.sql.Timestamp(new Date().getTime()));
		proposalAudit.setProposalId(proposal.getProposalId());
		proposalAudit.setStatus(proposal.getStatus());
		if (version != null) {
			proposalAudit.setVersionId(version);
		}
		OMSUser user = userRepository.findByUserId(userid);
		proposalAudit.setUserId(user);
		List<ProposalAudit> listOfProposalAudit = proposal.getAudits();
		if (null == listOfProposalAudit) {
			listOfProposalAudit = new ArrayList<ProposalAudit>();
		}
		listOfProposalAudit.add(proposalAudit);
		proposal.setAudits(listOfProposalAudit);
		return proposal;
	}

	@Override
	@Transactional
	public void proposalStatusUpdate(DelegateExecution execution) {
		Map<String, Object> map = execution.getVariables();
		Proposal proposal = (Proposal) map.get("proposal");
		addProposalAuditComments(proposal, (String) map.get("comment"), (Long) map.get("userid"),
				(Long) map.get("version"));
		updateProposalForWorkFlow(proposal, (Long) map.get("status"), (String) map.get("statusDesc"),
				(Long) map.get("version"), (String) map.get("comment"), (Long) map.get("userid"), false);
	}

	@Override
	public void updateProposalForWorkFlow(Proposal proposal, Long proposalStatus, String statusDesc, Long version,
			String comment, Long userid, boolean isWorkFlow) {
		try {

			if (proposal != null) {
				proposal.setUpdated(new Date());
				proposal.setUpdatedBy(userid);
				Set<ProposalVersion> updateVersions = new HashSet<ProposalVersion>();
				ProposalStatus status = proposalStatusRepository.findById(proposalStatus);
				for (ProposalVersion pv : proposal.getVersions()) {
					pv.setProposal(proposal);
					if (pv.getPreviousStatus() == null
							|| pv.getStatus().getStatusId() != pv.getPreviousStatus().getStatusId()) {
						pv.setPreviousStatus(pv.getStatus());
					}
					if (pv.getVersion() == version) {
						pv.setStatus(status);
						pv.setProopsalStatusDesc(statusDesc != null ? statusDesc : "");
						if (proposal.isSubmitted()) {
							pv.setSubmitted(true);
						}
					}
					updateVersions.add(pv);
				}
				proposal.setSubmitted(proposal.isSubmitted());
				proposal.setVersions(updateVersions);
				proposal.setStatus(status);
				proposalRepository.save(proposal);

			} else {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
						"Proposal with id {" + proposal.getProposalId() + "} does not exist.");
			}

		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, "Unable to update proposal", ex);
		}
	}

	@Override
	public List<String> getProposalAdmins() {
		List<OMSUser> mediaPlanners = userRepository.findByRoleName("Administrator");
		List<String> idList = mediaPlanners.stream().map(OMSUser::toString).collect(Collectors.toList());
		return idList;
	}

	@Override
	public List<String> getProposalMediaPlanners() {
		List<OMSUser> mediaPlanners = userRepository.findByRoleName("Media Planner");
		List<String> idList = mediaPlanners.stream().map(OMSUser::toString).collect(Collectors.toList());
		return idList;
	}

	@Override
	public List<String> getProposalPriceAdmins(DelegateExecution execution) {
		Map<String, Object> map = execution.getVariables();
		long statusid = (Long) map.get("status");
		ProposalStatus proposalStatus = proposalStatusRepository.findById(statusid);
		String status = proposalStatus.getName();
		List<OMSUser> users = null;
		switch (status) {
		case "Pricing Review":
			users = userRepository.findByRoleName("Pricing Manager");
			break;
		case "Legal Review":
			users = userRepository.findByRoleName("Legal");
			break;
		case "default":
			throw new OMSSystemException("unknown proposal status found while fetching admins");
		}
		List<String> idList = users.stream().map(OMSUser::toString).collect(Collectors.toList());
		return idList;
	}

	@Override
	public void proposalApproved(DelegateExecution execution) {
		Map<String, Object> map = execution.getVariables();
		Proposal proposal = (Proposal) map.get("proposal");
		proposal.setSubmitted(true);
		updateProposalForWorkFlow(proposal, (Long) map.get("status"), (String) map.get("statusdesc"),
				(Long) map.get("version"), (String) map.get("comment"), (Long) map.get("userid"), false);
		// create order
		omsOrderService.createOrder(proposal.getProposalId());
	}

	@Override
	public String getProposalAssignee(DelegateExecution execution)
			throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = execution.getVariables();
		Proposal proposal = (Proposal) map.get("proposal");
		proposal.setAssignTo(proposal.getMediaPlanner());
		updateProposal(proposal, proposal.getUpdatedBy());
		return proposal.getMediaPlanner().getId().toString();
	}

	@Override
	public PaginationResponseVO<Proposal> searchProposal(SearchRequestVO searchRequest) {
		Page<Proposal> pageResponse = null;
		PaginationResponseVO<Proposal> response = null;
		try {
			pageResponse = proposalRepository.findAll(ProposalSpecification.getProposalSpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Proposal>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		return response;
	}

	@Override
	public List<ProposalStatus> getAllProposalStatus() {
		return proposalStatusRepository.findByDeletedFalseOrderByNameAsc();
	}

	@Override
	@Transactional
	public Collection<ProposalDocument> addDocuments(ProposalDocuments proposalDocuments) {
		final Proposal proposal = this.getProposalById(proposalDocuments.getProposalId());
		if (proposal != null) {
			return uploadDocuments(proposalDocuments, proposal);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal with id {" + proposalDocuments.getProposalId() + "} does not exist.");
		}
	}

	private Collection<ProposalDocument> uploadDocuments(ProposalDocuments proposalDocuments, Proposal proposal) {
		try {
			proposalDocuments.getDocumentDetails().forEach(proposalDocument -> {
				proposalDocument.setProposal(proposal);
				// proposalDocument.setCreatedBy(proposalDocuments.getUserId());
				if (proposalDocument.getContent() != null) {
					Document doc = this.fileUploader.save(proposalDocument.toDocument());
					proposalDocument.setPath(doc.getPath());
					this.proposalDocumentRepository.save(proposalDocument);
				} else {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.BAD_REQUEST, "Attachment missing");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			this.removeDocuments(proposalDocuments);
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to upload documents.");
		}
		return proposalDocuments.getDocumentDetails();
	}

	private void removeDocuments(ProposalDocuments proposalDocuments) {
		proposalDocuments.getDocumentDetails().stream().filter(document -> document.getPath() != null)
				.forEach(document -> {
					this.fileUploader.delete(document.toDocument());
				});
	}

	@Override
	public Collection<ProposalDocument> getDocuments(Long proposalId) {
		final Proposal proposal = this.getProposalById(proposalId);
		if (proposal != null) {
			return proposal.getDocuments();
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal with id {" + proposalId + "} does not exist.");
		}
	}

	@Override
	public Collection<ProposalDocument> updateDocuments(ProposalDocuments proposalDocuments) {
		final Proposal proposal = this.getProposalById(proposalDocuments.getProposalId());
		if (proposal != null) {
			final Collection<ProposalDocument> updatedDocs = this.updateDocs(proposalDocuments, proposal);
			this.deleteDocs(proposalDocuments, proposal);
			return updatedDocs;
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal with id {" + proposalDocuments.getProposalId() + "} does not exist.");
		}
	}

	private Collection<ProposalDocument> updateDocs(ProposalDocuments proposalDocuments, Proposal proposal) {
		proposalDocuments.getDocumentDetails().forEach(proposalDocument -> {
			proposalDocument.setProposal(proposal);
			// proposalDocument.setCreatedBy(proposalDocuments.getUserId());
			if (proposalDocument.getProposalDocId() == null && proposalDocument.getContent() == null) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.BAD_REQUEST, "Attachment missing");
			}
			if (proposalDocument.getContent() != null) {
				Document doc = this.fileUploader.update(proposalDocument.toDocument());
				proposalDocument.setPath(doc.getPath());
			}
			this.proposalDocumentRepository.save(proposalDocument);
		});
		return proposalDocuments.getDocumentDetails();
	}

	private void deleteDocs(ProposalDocuments proposalDocuments, Proposal proposal) {
		proposal.getDocuments().stream().filter(document -> !proposalDocuments.getDocumentDetails().contains(document))
				.forEach(document -> {
					document.setProposal(proposal);
					this.fileUploader.delete(document.toDocument());
					this.proposalDocumentRepository.delete(document);
				});

	}

	@Override
	public String proposalRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProposalDocument getDocument(Long proposalId, Long proposalDocId) {
		if (this.getProposalById(proposalId) != null) {
			return this.proposalDocumentById(proposalDocId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal with id {" + proposalId + "} does not exist.");
		}
	}

	private ProposalDocument proposalDocumentById(Long proposalDocId) {
		ProposalDocument document = this.proposalDocumentRepository.findOne(proposalDocId);
		if (document != null) {
			try {
				document.setContent(FileUtil.getContent(document.getPath()));
			} catch (IOException ioe) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to retrieve proposal doc content.");
			}
			return document;
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Proposal document with id {" + proposalDocId + "} does not exist.");
		}
	}

	@PostConstruct
	public void init() {
		this.fileUploader = new FileSystemFileUploaderService(this.proposalDocUploadDir);
	}

	@Override
	public Proposal updateProposalVersion(ProposalVersion proposalVersion, Long proposalId, Long user)
			throws IllegalAccessException, InvocationTargetException {
		Proposal proposalDB = getProposalById(proposalId);
		proposalVersion.setProposal(proposalDB);
		Set<ProposalVersion> updateProposalVersions = new HashSet<ProposalVersion>();
		if (proposalDB.getVersions() != null && proposalVersion.getSeqNo() != null) {
			updateProposalVersions = proposalDB.getVersions().stream()
					.filter(pv -> !proposalVersion.getSeqNo().equals(pv.getSeqNo())).collect(Collectors.toSet());
		}
		proposalVersion.setVersion(updateProposalVersions.size() + 1L);
		updateProposalVersions.add(proposalVersion);
		proposalDB.setVersions(updateProposalVersions);
		proposalDB.setUpdated(new Date());
		proposalDB.setUpdatedBy(user);
		addProposalAudit(proposalDB, OMSConstants.UPDATE_PROPOSAL_VERSION);
		return updateProposal(proposalDB, user);
	}

	private ProposalVersion getProposalVersion(Proposal proposal) {
		ProposalVersion proposalVersion = new ProposalVersion();
		proposalVersion.setAdvertiserDiscount(proposal.getAdvertiserDiscount());
		proposalVersion.setBudget(proposal.getBudget());
		proposalVersion.setCurrency(proposal.getCurrency());
		proposalVersion.setDescription(proposal.getDescription());
		proposalVersion.setDueDate(proposal.getDueDate());
		proposalVersion.setEndDate(proposal.getEndDate());
		proposalVersion.setNotes(proposal.getNotes());
		proposalVersion.setPercentageOfClose(proposal.getPercentageOfClose());
		proposalVersion.setPricingModel(proposal.getPricingModel());
		proposalVersion.setSalesCategory(proposal.getSalesCategory());
		proposalVersion.setStartDate(proposal.getStartDate());
		proposalVersion.setOpportunity(proposal.getOpportunity());
		proposalVersion.setStatus(proposal.getStatus());
		proposalVersion.setSubmitted(false);
		proposalVersion.setProposal(proposal);
		if (proposal.getVersions() != null && !proposal.getVersions().isEmpty()) {
			proposalVersion.setVersion(proposal.getVersions().size() + 1L);

		} else {
			proposalVersion.setVersion(0L);
		}
		proposalVersion.setTerms(proposal.getTerms());
		proposalVersion.setStatus(proposal.getStatus());
		return proposalVersion;

	}

	@Override
	public List<Proposal> getProposalByStatus(Long id) throws OMSSystemException {
		try {
			return proposalRepository.getProposalByStatus(id);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Proposal with status {" + id + "} does not exist.", ex);
		}

	}
}
