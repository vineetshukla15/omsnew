package com.oms.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.LineItems;
import com.oms.service.LineItemsService;
import com.oms.viewobjects.LineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;


@RestController
@RequestMapping(value = "/lineItem")
public class LineItemsController extends BaseController {
	
	final static Logger logger = Logger.getLogger(LineItemsController.class);
	
	@Autowired
	private LineItemsService lineItemsService;
	
	// to get all line items
	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LineItems> listLineItems(){
		List<LineItems> lineItems=lineItemsService.getAllLineItems();
		logger.info("List of Line Items "+ lineItems.toString());
		return lineItems;
	}
	
	// to add line items
	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_NEW_LINEITEM)
	public @ResponseBody LineItems addLineItem(@RequestBody LineItems lineItems)throws OMSSystemException{
		lineItems.setCreatedBy(findUserIDFromSecurityContext());
		lineItems.setUpdatedBy(findUserIDFromSecurityContext());
		return lineItemsService.addLineItem(lineItems);
	}
	
	// to update line items
	@RequestMapping(method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_LINEITEM)
	public @ResponseBody LineItems updateLineItem(@RequestBody LineItems lineItem){
		return lineItemsService.updateLineItems(lineItem);
	}
	
	//to get line items by ID
	@RequestMapping(value = "/{lineItemId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LineItems getLineItemsById(@PathVariable Long lineItemId) throws OMSSystemException{
		return lineItemsService.getLineItemById(lineItemId);
	}
	
	// to delete line items
	@RequestMapping(value = "/{lineItemId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_LINEITEM)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeLineItems(@PathVariable("lineItemId") Long lineItemId) throws OMSSystemException{
		lineItemsService.removeLineItems(lineItemId);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PaginationResponseVO<LineItemListVO> searchLineItems(@RequestBody SearchRequestVO searchRequest ){ 
		return lineItemsService.searchLineItems(searchRequest);
	}
	
	@RequestMapping(value = "/getPrice", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPrice(@RequestBody LineItems lineItem){
		BigDecimal cost=BigDecimal.ZERO;
		cost=lineItemsService.getPrice(lineItem);
		return cost.toPlainString();
	}
	
}
