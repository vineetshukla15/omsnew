package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.OrderLineItems;
import com.oms.viewobjects.OrderLineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface OrderLineItemsService {
	public OrderLineItems addOrderLineItem(OrderLineItems orderLineItems);
	public OrderLineItems updateOrderLineItems(OrderLineItems orderLineItems)throws OMSSystemException;
    public List<OrderLineItems> getAllOrderLineItems();
	public OrderLineItems getOrderLineItemById(Long orderLineItemId) throws OMSSystemException;
	public void removeOrderLineItems(Long orderLineItemId)throws OMSSystemException;
	public PaginationResponseVO<OrderLineItemListVO> searchLineItems(SearchRequestVO searchRequest);

}
