package com.oms.controller;



import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.ads.dfp.axis.v201705.LineItem;
import com.oms.model.Order;
import com.oms.service.DFPService;
import com.oms.service.OMSOrderService;

@RestController
@RequestMapping(value = "/dfp", produces = MediaType.APPLICATION_JSON_VALUE)

public class DFPController {
	
	@Autowired
	DFPService dfpService;
	
	@Autowired
	OMSOrderService omsOrderService;
	
	@RequestMapping( value = "/createdfporders" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<com.google.api.ads.dfp.axis.v201705.Order> createDFPOrder(/*Order order*/) {//TODO uncomment method argument
		//TODO - remove below line of test code
		Order order = omsOrderService.getOrderById(new Long(28));
		
		System.out.println("Teh order id to be saved in DFP is "+order);
		
		return dfpService.createDFPOrder(order,true);
	}
	
	@RequestMapping( value = "/createdfplineitems",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<com.google.api.ads.dfp.axis.v201705.LineItem> createDFPLineItem(Order order,List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrders) {
		return dfpService.createLineItems(order, dfpOrders);
	}
	
	@RequestMapping( value = "/createdfporderswithlineitems" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<com.google.api.ads.dfp.axis.v201705.Order,Set<LineItem>> createDFPOrderWithLineItems(/*Order order*/) {//TODO uncomment method argument
		//TODO - remove below line of test code
		Order order = omsOrderService.getOrderById(new Long(28));		
		System.out.println("Teh order id to be saved in DFP is "+order);
		
		return dfpService.createDFPOrderWithLineItems(order);
	}

	
}
