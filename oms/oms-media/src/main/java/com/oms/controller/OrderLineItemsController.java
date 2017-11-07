package com.oms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.OrderLineItems;
import com.oms.service.OrderLineItemsService;
import com.oms.viewobjects.LineItemListVO;
import com.oms.viewobjects.OrderLineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;


@RestController
@RequestMapping(value = "/orderLineItem")
public class OrderLineItemsController extends BaseController {
	
	final static Logger logger = Logger.getLogger(LineItemsController.class);
	
	@Autowired
	private OrderLineItemsService orderLineItemsService;
	
	// to get all line items
	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderLineItems> listOrderLineItems(){
		List<OrderLineItems> orderLineItems=orderLineItemsService.getAllOrderLineItems();
		logger.info("List of Line Items "+ orderLineItems.toString());
		return orderLineItems;
	}
	
	// to update line items
	@RequestMapping(method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_LINEITEM)
	public @ResponseBody OrderLineItems updateOrderLineItem(@RequestBody OrderLineItems orderLineItemId){
		return orderLineItemsService.updateOrderLineItems(orderLineItemId);
	}
	
	//to get line items by ID
	@RequestMapping(value = "/{orderLineItemId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody OrderLineItems getOrderLineItemsById(@PathVariable Long orderLineItemId) throws OMSSystemException{
		return orderLineItemsService.getOrderLineItemById(orderLineItemId);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PaginationResponseVO<OrderLineItemListVO> searchLineItems(@RequestBody SearchRequestVO searchRequest ){ 
		return orderLineItemsService.searchLineItems(searchRequest);
	}
	
}
