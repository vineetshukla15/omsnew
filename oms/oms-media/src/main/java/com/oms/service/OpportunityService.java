package com.oms.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Opportunity;
import com.oms.model.OpportunityDocument;
import com.oms.model.OpportunityDocuments;
import com.oms.model.OpportunityVersion;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface OpportunityService {
	public List<Opportunity> getAllOpportunity();
	public Opportunity getOpportunityById(Long opportunityId) throws OMSSystemException;
	public Opportunity addOpportunity(Opportunity opportunity, Long userId);
	public void deleteOpportunity(Long opportunityId);
	public Opportunity updateOpportunity(Opportunity opportuntiy, Long userId);
	public Opportunity copyOpportunity(Long opportunityId, Boolean isVersion,Long userId) throws OMSSystemException;
	public PaginationResponseVO<Opportunity> searchOpportunity(SearchRequestVO searchRequest);
	Collection<OpportunityDocument> addDocuments(OpportunityDocuments opportunityDocuments);
	Collection<OpportunityDocument> updateDocuments(OpportunityDocuments opportunityDocuments);
	Collection<OpportunityDocument> getDocuments(Long opportunityId);
	OpportunityDocument getDocument(Long opportunityId, Long documentId);
	Opportunity updateOpportunityVersion(OpportunityVersion opportunityVersion,
			Long opportunityId,Long userId);
}
