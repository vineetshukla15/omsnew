package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.Creative;
import com.oms.model.Order;

@Repository
public interface OmsOrderRepository extends SoftDeleteRepository<Order, Long>, JpaSpecificationExecutor<Order>,
		PagingAndSortingRepository<Order, Long> {
	
	@Query("select o from Order o where o.pushed = ?1")
	List<Order> findOrdersForVPZ(boolean pushedToVPZ);
		
}
