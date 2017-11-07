package com.oms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.OpportunityDocument;

@Repository
public interface OpportunityDocumentRepository
		extends JpaSpecificationExecutor<OpportunityDocument>, PagingAndSortingRepository<OpportunityDocument, Long> {

}
