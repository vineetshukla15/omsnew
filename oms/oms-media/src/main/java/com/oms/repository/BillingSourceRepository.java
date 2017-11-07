package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.BillingSource;

@Repository
public interface BillingSourceRepository extends SoftDeleteRepository<BillingSource, Long>,
		JpaSpecificationExecutor<BillingSource>, PagingAndSortingRepository<BillingSource, Long> {

	public List<BillingSource> findByDeletedFalseOrderByBillingSourceIdDesc();
	
	public List<BillingSource> findByDeletedFalseOrderByNameAsc();
	
}
