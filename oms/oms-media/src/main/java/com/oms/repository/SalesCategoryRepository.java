package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.SalesCategory;

@Repository
public interface SalesCategoryRepository extends SoftDeleteRepository<SalesCategory, Long>,
		JpaSpecificationExecutor<SalesCategory>, PagingAndSortingRepository<SalesCategory, Long> {

	public List<SalesCategory> findByDeletedFalseOrderBySalesCatagoryIdDesc();
	
	List<SalesCategory> findByDeletedFalseOrderByNameAsc();
	
}
