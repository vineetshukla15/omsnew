package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.ProposalStatus;

	@Repository
	public interface ProposalStatusRepository extends SoftDeleteRepository<ProposalStatus, Long>, JpaSpecificationExecutor<ProposalStatus>,
	PagingAndSortingRepository<ProposalStatus, Long> {
		
		ProposalStatus findByName(String name);
		
		List<ProposalStatus> findByDeletedFalseOrderByNameAsc();
		
}
