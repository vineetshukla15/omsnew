package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Proposal;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.ProposalStatus;
import com.oms.model.ProposalVersion;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;



@Service
public interface ProposalService {
	
public List<Proposal> getAllProposals();
	
	public Proposal getProposalById(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	public Proposal addProposal(Proposal proposalDTO, Long userId) throws IllegalAccessException, InvocationTargetException, Exception;

	public Proposal updateProposal(Proposal proposalDTO,Long userid) throws IllegalAccessException, InvocationTargetException;

	public void deleteProposal(Long proposalId) throws IllegalAccessException, InvocationTargetException, OMSSystemException;

	public Proposal copyProposal(Long proposalId, Boolean isVersion, Long userId)
			throws IllegalAccessException, InvocationTargetException,
			OMSSystemException;

	public PaginationResponseVO<Proposal> searchProposal(SearchRequestVO searchRequest);
	public List<ProposalStatus> getAllProposalStatus();
	
	Collection<ProposalDocument> addDocuments(ProposalDocuments proposalDocuments);

	Collection<ProposalDocument> getDocuments(Long proposalId);

	Collection<ProposalDocument> updateDocuments(ProposalDocuments proposalDocuments);

	String proposalRole();

	List<String> getProposalMediaPlanners();

	ProposalDocument getDocument(Long proposalId, Long proposalDocId);

	void proposalStatusUpdate(DelegateExecution execution);

	List<String> getProposalAdmins();

	void proposalApproved(DelegateExecution execution);

	List<String> getProposalPriceAdmins(DelegateExecution execution);

	public Proposal updateProposalVersion(ProposalVersion proposalVersion,
			Long proposalId, Long user) throws IllegalAccessException, InvocationTargetException;

	void updateProposalForWorkFlow(Proposal proposal, Long proposalStatus, String statusDesc,
			Long version, String comment, Long userid, boolean isWorkflow);

	String getProposalAssignee(DelegateExecution execution) throws IllegalAccessException, InvocationTargetException;

	void addProposalAudit(ProposalVersion proposalVersion);

	void addProposalAudit(Proposal proposal,String messages);

	Proposal addProposalAuditComments(Proposal proposal, String comments,
			Long userid, Long version);

	List<Proposal> getProposalByStatus(Long id) throws OMSSystemException;
	

}
