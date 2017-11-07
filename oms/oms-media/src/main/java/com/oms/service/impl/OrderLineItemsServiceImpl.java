package com.oms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.LineItems;
import com.oms.model.OrderLineItems;
import com.oms.model.specification.LineItemSpecification;
import com.oms.model.specification.OrderLineItemSpecification;
import com.oms.repository.OrderLineItemsRepository;
import com.oms.service.OrderLineItemsService;
import com.oms.viewobjects.LineItemListVO;
import com.oms.viewobjects.OrderLineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class OrderLineItemsServiceImpl implements OrderLineItemsService {
	
	final static Logger LOGGER = Logger.getLogger(RateCardServiceImpl.class);
	
	@Autowired
	private OrderLineItemsRepository orderLineItemsRepository;

	@Transactional
	public OrderLineItems addOrderLineItem(OrderLineItems lineItems) throws OMSSystemException {
		try{
			return orderLineItemsRepository.save(lineItems);
		}catch(Exception e){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public OrderLineItems updateOrderLineItems(OrderLineItems lineItems) throws OMSSystemException {
		if(lineItems.getLineItemId()!=null && Boolean.FALSE.equals(lineItems.isDeleted())){
			orderLineItemsRepository.save(lineItems);
			return lineItems;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Line Item with {" + lineItems.getLineItemId() + "} does not exist.");
	}

	@Transactional
	public List<OrderLineItems> getAllOrderLineItems() {
		List<OrderLineItems> lineItems = null;
		try {
			lineItems=new ArrayList<OrderLineItems>();
			lineItems.addAll(orderLineItemsRepository.findAll());
		//	lineItems.forEach(li->{System.out.println(li.getProposal().getProposalId());});
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive LineItems information",ex);
		}
		return lineItems;
	}

	@Transactional
	public OrderLineItems getOrderLineItemById(Long lineItemId) throws OMSSystemException {
		try{
		if(orderLineItemsRepository.findById(lineItemId)!=null){
			return orderLineItemsRepository.findById(lineItemId);
		}
		}catch(Exception ex){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive line item information",ex);
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Line Item with {" + lineItemId + "} does not exist.");
		
	}

	@Transactional
	@Override
	public void removeOrderLineItems(Long lineItemId) throws OMSSystemException{
		try{
			OrderLineItems lineItem = orderLineItemsRepository.findById(lineItemId);
		if(lineItem != null && Boolean.FALSE.equals(lineItem.isDeleted())){
			orderLineItemsRepository.softDelete(lineItemId);
		} else{
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Line Item with {" + lineItemId + "} does not exist or already deleted.");
		}
		}catch(OMSSystemException oex){
			throw oex;
		}catch(Exception ex){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Unable to remove this line item",ex);
		}
		
	}

	@Override
	public PaginationResponseVO<OrderLineItemListVO> searchLineItems(SearchRequestVO searchRequest) {
		Page<OrderLineItems> pageResponse = null;
		PaginationResponseVO<OrderLineItemListVO> response = null; 
		try {
			pageResponse = orderLineItemsRepository.findAll(OrderLineItemSpecification.getOrderLineItemSpecification(searchRequest),SearchUtil.getPageable(searchRequest));
			List<OrderLineItemListVO> itemListVOs=new ArrayList<OrderLineItemListVO>();
			pageResponse.getContent().forEach(li->{
				itemListVOs.add( setLineItemListVOObject(li));
			});
			response= new PaginationResponseVO<OrderLineItemListVO>(pageResponse.getTotalElements(), searchRequest.getDraw(), pageResponse.getTotalElements(), itemListVOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	private OrderLineItemListVO setLineItemListVOObject(OrderLineItems lineItems){
		OrderLineItemListVO lineItemListVO=new OrderLineItemListVO();
		lineItemListVO.setEndDate(lineItems.getFlightEndDate());
		lineItemListVO.setStartDate(lineItems.getFlightStartdate());
		BigDecimal impression=new BigDecimal(lineItems.getQuantity());
		BigDecimal cost = lineItems.getProposedCost().multiply(impression);
		lineItemListVO.setLineItemCost(cost);
		lineItemListVO.setProductId(lineItems.getProduct().getProductId());
		lineItemListVO.setType(lineItems.getPriority());
		lineItemListVO.setLineItemName(lineItems.getName());
		lineItemListVO.setImpressions(lineItems.getQuantity());		
		lineItemListVO.setLineItemId(lineItems.getLineItemId());
		lineItemListVO.setProposalId(lineItems.getProposal().getProposalId());
		lineItemListVO.setOrderLineItemId(lineItems.getOrderLineItemId());
		//lineItemListVO.setProposalName("test_proposal");
		return lineItemListVO;
	}
	
	
}
