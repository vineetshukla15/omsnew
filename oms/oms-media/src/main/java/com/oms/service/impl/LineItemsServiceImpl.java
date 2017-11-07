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
import com.oms.model.Product;
import com.oms.model.specification.LineItemSpecification;
import com.oms.repository.LineItemsRepository;
import com.oms.service.LineItemsService;
import com.oms.viewobjects.LineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class LineItemsServiceImpl implements LineItemsService {
	
	final static Logger LOGGER = Logger.getLogger(RateCardServiceImpl.class);
	
	@Autowired
	private LineItemsRepository lineItemsRepository;

	@Transactional
	public LineItems addLineItem(LineItems lineItems) throws OMSSystemException {
		try{
			return lineItemsRepository.save(lineItems);
		}catch(Exception e){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public LineItems updateLineItems(LineItems lineItems) throws OMSSystemException {
		if(lineItems.getLineItemId()!=null && Boolean.FALSE.equals(lineItems.isDeleted())){
			lineItemsRepository.save(lineItems);
			return lineItems;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Line Item with {" + lineItems.getLineItemId() + "} does not exist.");
	}

	@Transactional
	public List<LineItems> getAllLineItems() {
		List<LineItems> lineItems = null;
		try {
			lineItems=new ArrayList<LineItems>();
			lineItems.addAll(lineItemsRepository.findAll());
		//	lineItems.forEach(li->{System.out.println(li.getProposal().getProposalId());});
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive LineItems information",ex);
		}
		return lineItems;
	}

	@Transactional
	public LineItems getLineItemById(Long lineItemId) throws OMSSystemException {
		try{
		if(lineItemsRepository.findById(lineItemId)!=null){
			return lineItemsRepository.findById(lineItemId);
		}
		}catch(Exception ex){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive line item information",ex);
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Line Item with {" + lineItemId + "} does not exist.");
		
	}

	@Transactional
	@Override
	public void removeLineItems(Long lineItemId) throws OMSSystemException{
		try{
			LineItems lineItem = lineItemsRepository.findById(lineItemId);
		if(lineItem != null && Boolean.FALSE.equals(lineItem.isDeleted())){
			lineItemsRepository.softDelete(lineItemId);
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
	public PaginationResponseVO<LineItemListVO> searchLineItems(SearchRequestVO searchRequest) {
		Page<LineItems> pageResponse = null;
		PaginationResponseVO<LineItemListVO> response = null; 
		try {
			pageResponse = lineItemsRepository.findAll(LineItemSpecification.getLineItemSpecification(searchRequest),SearchUtil.getPageable(searchRequest));
			List<LineItemListVO> itemListVOs=new ArrayList<LineItemListVO>();
			pageResponse.getContent().forEach(li->{
				itemListVOs.add(setLineItemListVOObject(li));
			});
			response= new PaginationResponseVO<LineItemListVO>(pageResponse.getTotalElements(), searchRequest.getDraw(), pageResponse.getTotalElements(), itemListVOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public BigDecimal getPrice(LineItems lineItem) throws OMSSystemException{
		Product product = lineItem.getProduct();
		BigDecimal proposedCost;
		if (product.getRateCard() != null) {
			BigDecimal basePrice = product.getRateCard().getBasePrice();
			proposedCost=basePrice;
			if (product.getRateCard().getSeasonalDiscounts() != null
					&& product.getRateCard().getSeasonalDiscounts().size() > 0) {
				proposedCost = lineItem.calculteSessionalDiscount(lineItem, product.getRateCard().getSeasonalDiscounts(),
						basePrice);
			} else {
				proposedCost = basePrice;
			}
			if (product.getRateCard().getPremiums() != null && product.getRateCard().getPremiums().size() > 0) {
				proposedCost = lineItem.calcultePremium(product.getRateCard().getPremiums(), proposedCost);
			}
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.FAILED_DEPENDENCY,
					"Pricing calculation failed! Rate card do not exist!");
		}
		return proposedCost;
	}
	
	private LineItemListVO setLineItemListVOObject(LineItems lineItems){
		LineItemListVO lineItemListVO=new LineItemListVO();
		lineItemListVO.setEndDate(lineItems.getFlightEndDate());
		lineItemListVO.setStartDate(lineItems.getFlightStartdate());
		BigDecimal impression=new BigDecimal(lineItems.getQuantity());
		BigDecimal cost = lineItems.getProposedCost().multiply(impression);
		lineItemListVO.setLineItemCost(cost);
		lineItemListVO.setProductName(lineItems.getProduct().getName());
		lineItemListVO.setStatus(lineItems.getProposal().getStatus().getName());
		lineItemListVO.setType(lineItems.getPriority());
		lineItemListVO.setLineItemName(lineItems.getName());
		lineItemListVO.setImpressions(lineItems.getQuantity());
		lineItemListVO.setProposalName(lineItems.getProposal().getName());
		lineItemListVO.setLineItemId(lineItems.getLineItemId());
		lineItemListVO.setProposalId(lineItems.getProposal().getProposalId());
		return lineItemListVO;
	}
}
