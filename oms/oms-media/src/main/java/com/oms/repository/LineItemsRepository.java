package com.oms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.LineItems;

@Repository
public interface LineItemsRepository extends SoftDeleteRepository<LineItems, Long>, JpaSpecificationExecutor<LineItems>,
		PagingAndSortingRepository<LineItems, Long> {

}
