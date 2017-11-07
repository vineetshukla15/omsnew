package com.oms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.ProposalDocument;

@Repository
public interface ProposalDocumentRepository
		extends JpaSpecificationExecutor<ProposalDocument>, PagingAndSortingRepository<ProposalDocument, Long> {

}
