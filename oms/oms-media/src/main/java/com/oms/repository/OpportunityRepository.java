package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.Opportunity;

@Repository
public interface OpportunityRepository extends SoftDeleteRepository<Opportunity, Long>,
		JpaSpecificationExecutor<Opportunity>, PagingAndSortingRepository<Opportunity, Long> {

	public List<Opportunity> findAllByOrderByOpportunityIdDesc();

	public List<Opportunity> findByDeletedFalseOrderByNameAsc();

}
