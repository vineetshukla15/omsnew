package com.oms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.Order;
import com.oms.model.OrderLineItems;

@Repository
public interface OmsOrderLineItemRepository extends SoftDeleteRepository<OrderLineItems, Long>, JpaSpecificationExecutor<Order>,
		PagingAndSortingRepository<OrderLineItems, Long> {

	
}
