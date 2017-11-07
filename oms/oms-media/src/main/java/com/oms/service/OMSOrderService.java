package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Opportunity;
import com.oms.model.Order;
import com.oms.model.OrderLineItems;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;
@Service
public interface OMSOrderService {
	
	public Order createOrder(Long proposalId);
	public Order updateOrder(Order order);
//	public Order getOrderById(Long orderId) throws OMSSystemException, IllegalAccessException, InvocationTargetException;
//	public List<Order> getAllOrders();
	public List<Order> findOrdersForVPZ() throws OMSSystemException, IllegalAccessException, InvocationTargetException;
	OrderLineItems updateOrderLineItem(OrderLineItems orderLineItem);
	public List<Order> getOrderList();
	public PaginationResponseVO<Order> searchOrder(SearchRequestVO searchRequest);
	public Order getOrderById(Long orderId);
}
