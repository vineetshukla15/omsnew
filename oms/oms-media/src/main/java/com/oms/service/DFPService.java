package com.oms.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.api.ads.dfp.axis.v201705.LineItem;
import com.oms.model.Order;

@Service
public interface DFPService {

	public List<com.google.api.ads.dfp.axis.v201705.Order> createDFPOrder(Order order,boolean createLineItem);

	public Set<LineItem> createLineItems(Order order, List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrderList);
	
	public Map<com.google.api.ads.dfp.axis.v201705.Order,Set<LineItem>> createDFPOrderWithLineItems(Order order);
}
