package com.oms.dfp.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.api.ads.dfp.axis.v201705.LineItem;
import com.google.api.ads.dfp.axis.v201705.Order;

@Service
public interface DFPOrderService {
	
	public List<Order> createDFPOrder(Order orders);
	public Set<LineItem> createDFPLineItem(Set<LineItem> lineitems);
}
