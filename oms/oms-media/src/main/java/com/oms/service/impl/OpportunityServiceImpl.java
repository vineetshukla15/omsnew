package com.oms.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oms.commons.utils.FileUtil;
import com.oms.commons.utils.SearchUtil;
import com.oms.enums.ProposalStatus;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Opportunity;
import com.oms.model.OpportunityDocument;
import com.oms.model.OpportunityDocuments;
import com.oms.model.OpportunityVersion;
import com.oms.model.Proposal;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.specification.OpportunitySpecification;
import com.oms.repository.OpportunityDocumentRepository;
import com.oms.repository.OpportunityRepository;
import com.oms.repository.OpportunityVersionRepository;
import com.oms.repository.ProposalRepository;
import com.oms.repository.ProposalStatusRepository;
import com.oms.service.FileUploaderService;
import com.oms.service.OpportunityService;
import com.oms.service.ProposalService;
import com.oms.viewobjects.Document;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class OpportunityServiceImpl implements OpportunityService {
	final static Logger logger = Logger.getLogger(OpportunityServiceImpl.class);

	@Autowired
	OpportunityRepository opportunityRepository;

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private ProposalStatusRepository proposalStatusRepository;

	@Autowired
	private OpportunityDocumentRepository opportunityDocumentRepository;

	@Autowired
	private OpportunityVersionRepository opportunityVersionRepository;

	@Autowired
	private ProposalService proposalService;

	@Value("${opportunity.document.upload-dir}")
	private String opportunityDocUploadDir;

	private FileUploaderService fileUploader;

	@Transactional
	public List<Opportunity> getAllOpportunity() {
		logger.debug("Getting Opportunities...");
		List<Opportunity> opportunityList = null;
		try {
			opportunityList = opportunityRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Opportunities information", ex);
		}
		return opportunityList;
	}

	@Override
	public Opportunity getOpportunityById(Long opportunityId) throws OMSSystemException {
		logger.info("Extracting Opportunity using id...");
		try {
			Opportunity opportunity = opportunityRepository.findById(opportunityId);

			if (opportunity != null) {
				return opportunity;

			} else
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
						"Opportunity with {" + opportunityId + "} does not exist.");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Unable to fetch Opportunity id {" + opportunityId + "} ");
		}

	}

	@Transactional
	public Opportunity addOpportunity(Opportunity opportunity, Long userId) throws OMSSystemException {
		logger.info("Adding Opportunity...");
		try {
			opportunity.setCreated(new Date());
			opportunity.setCreatedBy(userId);
			opportunity.setUpdated(new Date());
			opportunity.setUpdatedBy(userId);
			Opportunity opportunityNew=opportunityRepository.save(opportunity);
			if (opportunity.isSubmitted()) {
				opportunity.setOpportunityId(opportunityNew.getOpportunityId());
				proposalService.addProposal(this.buildProposal(opportunity),userId);
			}
			return opportunityNew;
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	@Override
	public void deleteOpportunity(Long opportunityId) {
		logger.info("Removing Opportunity...");
		Opportunity opportunity = opportunityRepository.findById(opportunityId);
		if (opportunity != null && Boolean.FALSE.equals(opportunity.isDeleted())) {
			opportunityRepository.softDelete(opportunityId);
			this.deleteDocuments(opportunity);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Opportunity with id {" + opportunityId + "} does not exist or already deleted.");
		}
	}

	// Hard delete opportunity documents
	private void deleteDocuments(Opportunity opportunity) {
		Collection<OpportunityDocument> opportunityDocuments = opportunity.getDocuments();
		opportunityDocuments.forEach(opportunityDocument -> {
			opportunityDocument.setOpportunity(opportunity);
			this.fileUploader.delete(opportunityDocument.toDocument());
		});
		this.opportunityDocumentRepository.delete(opportunityDocuments);
	}

	@Transactional
	public Opportunity updateOpportunity(Opportunity opportunity, Long userId) {
		logger.info("Updating Opportunity...");
		try{
			Opportunity opportunityDB = getOpportunityById(opportunity.getOpportunityId());
			if (opportunityDB.getOpportunityId() != null && Boolean.FALSE.equals(opportunity.isDeleted())) {
			final boolean alreadySubmitted = opportunityDB.isSubmitted();
			opportunityRepository.save(opportunity);
			if (!alreadySubmitted && opportunity.isSubmitted()) {
				proposalService.addProposal(this.buildProposal(opportunity),userId);
			}
			return opportunity;
		}else{
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Opportunity with id {" + opportunity.getOpportunityId() + "} does not exist.");
		}
		}catch(Exception ex){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"unable to update Opportunity id {" + opportunity.getOpportunityId() + "}");
		}
	}


	@Transactional
	public Opportunity copyOpportunity(Long opportunityId, Boolean isVersion, Long userId) throws OMSSystemException {
		Opportunity opportunity = getOpportunityById(opportunityId);
		if (BooleanUtils.isTrue(isVersion)) {
			return addOpportunityVersion(opportunity, userId);
		} else {
			return copyOpportunity(opportunity,userId);
		}
	}

	public Opportunity copyOpportunity(Opportunity opportunity,Long userid) {
		Opportunity copiedOpportunity = new Opportunity();
		copiedOpportunity.setAdvertiser(opportunity.getAdvertiser());
		copiedOpportunity.setAdvertiserDiscount(opportunity.getAdvertiserDiscount());
		copiedOpportunity.setAgency(opportunity.getAgency());
		copiedOpportunity.setBillingSource(opportunity.getBillingSource());
		copiedOpportunity.setBudget(opportunity.getBudget());
		copiedOpportunity.setCurrency(opportunity.getCurrency());
		copiedOpportunity.setDescription(opportunity.getDescription());
		copiedOpportunity.setDueDate(opportunity.getDueDate());
		copiedOpportunity.setEndDate(opportunity.getEndDate());
		copiedOpportunity.setName(opportunity.getName());
		copiedOpportunity.setNotes(opportunity.getNotes());
		copiedOpportunity.setPercentageOfClose(opportunity.getPercentageOfClose());
		copiedOpportunity.setPricingModel(opportunity.getPricingModel());
		copiedOpportunity.setSalesCategory(opportunity.getSalesCategory());
		copiedOpportunity.setSalesPerson(opportunity.getSalesPerson());
		copiedOpportunity.setStartDate(opportunity.getStartDate());
		copiedOpportunity.setTerms(opportunity.getTerms());
		copiedOpportunity.setTrafficker(opportunity.getTrafficker());
		return addOpportunity(copiedOpportunity,userid);
	}

	private Opportunity addOpportunityVersion(Opportunity opportunity,Long userId) {

		OpportunityVersion opportunityVersion = new OpportunityVersion();
		opportunityVersion.setAdvertiserDiscount(opportunity.getAdvertiserDiscount());
		opportunityVersion.setBudget(opportunity.getBudget());
		opportunityVersion.setCurrency(opportunity.getCurrency());
		opportunityVersion.setDescription(opportunity.getDescription());
		opportunityVersion.setDueDate(opportunity.getDueDate());
		opportunityVersion.setEndDate(opportunity.getEndDate());
		opportunityVersion.setName(opportunity.getName());
		opportunityVersion.setNotes(opportunity.getNotes());

		opportunityVersion.setOpportunity(opportunity);
		opportunityVersion.setPercentageOfClose(opportunity.getPercentageOfClose());
		opportunityVersion.setPricingModel(opportunity.getPricingModel());
		opportunityVersion.setSalesCategory(opportunity.getSalesCategory());
		opportunityVersion.setStartDate(opportunity.getStartDate());
		opportunityVersion.setTerms(opportunity.getTerms());
		Long maxversion = opportunityVersionRepository.findMaxVersionOfOpportunity(opportunity.getOpportunityId());
		if (maxversion == null) {
			maxversion = 0L;
		}
		opportunityVersion.setVersion(maxversion + 1L);
		opportunity.getVersions().add(opportunityVersion);
		return updateOpportunity(opportunity,userId);
	}

	private Proposal buildProposal(final Opportunity opportunity) {
		Proposal proposal = new Proposal();
		proposal.setName(opportunity.getName());
		proposal.setDescription(opportunity.getDescription());
		proposal.setStatus(this.proposalStatusRepository.findByName(ProposalStatus.DRAFT.statusName()));
		proposal.setAgency(opportunity.getAgency());
		proposal.setStartDate(opportunity.getStartDate());
		proposal.setEndDate(opportunity.getEndDate());
		proposal.setDueDate(opportunity.getDueDate());
		proposal.setCurrency(opportunity.getCurrency());
		proposal.setPricingModel(opportunity.getPricingModel());
		proposal.setAdvertiser(opportunity.getAdvertiser());
		proposal.setAdvertiserDiscount(opportunity.getAdvertiserDiscount());
		proposal.setMediaPlanner(opportunity.getMediaPlanner());
		if(opportunity.getMediaPlanner()!=null){
			proposal.setAssignTo(opportunity.getMediaPlanner());
		}
		proposal.setBudget(opportunity.getBudget());
		proposal.setSalesPerson(opportunity.getSalesPerson());
		proposal.setTrafficker(opportunity.getTrafficker());
		proposal.setBillingSource(opportunity.getBillingSource());
		proposal.setTerms(opportunity.getTerms());
		proposal.setPercentageOfClose(opportunity.getPercentageOfClose());
		proposal.setSalesCategory(opportunity.getSalesCategory());
		proposal.setSubmitted(false);
		proposal.setNotes(opportunity.getNotes());
		proposal.setProposalDiscount(0.0F);
		proposal.setOpportunity(opportunity);
		proposal.setCreatedBy(opportunity.getUpdatedBy());
		proposal.setUpdatedBy(opportunity.getUpdatedBy());
		proposal.setUpdated(new Date());
		proposal.setCreated(new Date());
		return proposal;
	}

	@Override
	public PaginationResponseVO<Opportunity> searchOpportunity(SearchRequestVO searchRequest) {
		Page<Opportunity> pageResponse = null;
		PaginationResponseVO<Opportunity> response = null;
		try {
			pageResponse = opportunityRepository.findAll(
					OpportunitySpecification.getOpportunitySpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Opportunity>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		System.out.println("");
		return response;
	}

	@Override
	@Transactional
	public Collection<OpportunityDocument> addDocuments(OpportunityDocuments opportunityDocuments) {
		final Opportunity opportunity = this.getOpportunityById((opportunityDocuments.getOpportunityId()));
		final Collection<OpportunityDocument> uploadDocuments = this.uploadDocuments(opportunityDocuments, opportunity);
		return uploadDocuments;
	}

	private Collection<OpportunityDocument> uploadDocuments(OpportunityDocuments opportunityDocuments,
			Opportunity opportunity) {
		try {
			opportunityDocuments.getDocumentDetails().forEach(opportunityDocument -> {
				opportunityDocument.setOpportunity(opportunity);
				//opportunityDocument.setCreatedBy(opportunityDocuments.getUserId());
				if (opportunityDocument.getContent() != null) {
					Document doc = this.fileUploader.save(opportunityDocument.toDocument());
					opportunityDocument.setPath(doc.getPath());
					this.opportunityDocumentRepository.save(opportunityDocument);
				} else {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.BAD_REQUEST, "Attachment missing");
				}
			});
		} catch (Exception e) {
			this.removeDocuments(opportunityDocuments);
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to upload documents.");
		}
		return opportunityDocuments.getDocumentDetails();
	}

	private void removeDocuments(OpportunityDocuments opportunityDocuments) {
		opportunityDocuments.getDocumentDetails().stream().filter(document -> document.getPath() != null)
				.forEach(document -> {
					this.fileUploader.delete(document.toDocument());
				});
	}

	@Override
	@Transactional
	public Collection<OpportunityDocument> updateDocuments(OpportunityDocuments opportunityDocuments) {
		final Opportunity opportunity = this.getOpportunityById((opportunityDocuments.getOpportunityId()));
		final Collection<OpportunityDocument> updatedDocs = this.updateDocs(opportunityDocuments, opportunity);
		this.deleteDocs(opportunityDocuments, opportunity);
		if (opportunity.isSubmitted()) {
			this.copyDocumentsToProposal(opportunity, opportunityDocuments);
		}
		return updatedDocs;
	}

	private Collection<OpportunityDocument> updateDocs(OpportunityDocuments opportunityDocuments,
			Opportunity opportunity) {
		opportunityDocuments.getDocumentDetails().forEach(opportunityDocument -> {
			opportunityDocument.setOpportunity(opportunity);
			//opportunityDocument.setCreatedBy(opportunityDocuments.getUserId());
			if (opportunityDocument.getOpportinutyDocId() == null && opportunityDocument.getContent() == null) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.BAD_REQUEST, "Attachment missing");
			}
			if (opportunityDocument.getContent() != null) {
				Document doc = this.fileUploader.update(opportunityDocument.toDocument());
				opportunityDocument.setPath(doc.getPath());
			}
			this.opportunityDocumentRepository.save(opportunityDocument);
		});
		return opportunityDocuments.getDocumentDetails();
	}

	private void deleteDocs(OpportunityDocuments opportunityDocuments, Opportunity opportunity) {
		opportunity.getDocuments().stream()
				.filter(document -> !opportunityDocuments.getDocumentDetails().contains(document)).forEach(document -> {
					document.setOpportunity(opportunity);
					this.fileUploader.delete(document.toDocument());
					this.opportunityDocumentRepository.delete(document);
				});
	}

	@Override
	public Collection<OpportunityDocument> getDocuments(Long opportunityId) {
		return this.getOpportunityById((opportunityId)).getDocuments();
	}

	private void copyDocumentsToProposal(Opportunity opportunity, OpportunityDocuments opportunityDocuments) {
		final List<ProposalDocument> proposalDocuments = opportunity.getDocuments().stream().map(doc -> {
			doc.setContent(doc.getContent() != null ? doc.getContent() : fileContent(doc.getPath()));
			ProposalDocument proposalDocument = new ProposalDocument(doc);
			proposalDocument.setProposal(opportunity.getProposal());
			//proposalDocument.setCreatedBy(opportunityDocuments.getUserId());
			return proposalDocument;
		}).collect(Collectors.toList());
		this.proposalService.addDocuments(
				new ProposalDocuments(proposalDocuments, opportunity.getProposal(), opportunityDocuments.getUserId()));
	}

	private byte[] fileContent(String path) {
		try {
			return FileUtil.getContent(path);
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to retrieve doc content");
		}
	}

	@Override
	public OpportunityDocument getDocument(Long opportunityId, Long documentId) {
		this.getOpportunityById(opportunityId);
		return this.opportunityDocumentById(documentId);
	}

	private OpportunityDocument opportunityDocumentById(Long documentId) {
		OpportunityDocument document = this.opportunityDocumentRepository.findOne(documentId);
		if (document != null) {
			try {
				document.setContent(FileUtil.getContent(document.getPath()));
			} catch (IOException ioe) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to retrieve opportunity doc content.");
			}
			return document;
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Opportunity document with id {" + documentId + "} does not exist.");
		}
	}

	@PostConstruct
	public void init() {
		this.fileUploader = new FileSystemFileUploaderService(this.opportunityDocUploadDir);
	}

	@Override
	public Opportunity updateOpportunityVersion(OpportunityVersion opportunityVersion, Long opportunityId, Long userID) {

		Opportunity opportunity = getOpportunityById(opportunityId);
		opportunityVersion.setOpportunity(opportunity);
		Set<OpportunityVersion> opportunityVersions = opportunity.getVersions();
		Set<OpportunityVersion> existingopportunityVersion = opportunityVersions.stream()
				.filter(ov -> !opportunityVersion.getSeqNo().equals(ov.getSeqNo())).collect(Collectors.toSet());
		existingopportunityVersion.add(opportunityVersion);
		opportunity.setVersions(existingopportunityVersion);
		return updateOpportunity(opportunity,userID);

	}

}
