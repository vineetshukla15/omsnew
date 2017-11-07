package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.AudienceTargetValues;
import com.oms.model.LineItems;
import com.oms.model.LineitemTarget;
import com.oms.model.Order;
import com.oms.model.OrderAudienceTargetValues;
import com.oms.model.OrderLineItems;
import com.oms.model.OrderLineitemTarget;
import com.oms.model.Proposal;
import com.oms.model.specification.OrderSpecification;
import com.oms.repository.OmsOrderRepository;
import com.oms.repository.OrderLineItemsRepository;
import com.oms.repository.ProposalRepository;
import com.oms.service.OMSOrderService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class OMSOrderServiceImpl implements OMSOrderService {

	final static Logger logger = Logger.getLogger(OMSOrderServiceImpl.class);

	@Autowired
	OmsOrderRepository omsOrderRepository;

	@Autowired
	OrderLineItemsRepository omsOrderLineItemRepository;

	@Autowired
	ProposalRepository proposalRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Order createOrder(Long proposalId) {
		try {

			Proposal proposal = proposalRepository.findByProposalId(proposalId);
			// entityManager.detach(proposal);
			logger.debug("Here is proposal");
			Order order = new Order();

			order.setProposal(proposal);
			order.setName(proposal.getName());
			order.setDescription(proposal.getDescription());
		/*	order.setOpportunityId(proposal.getOpportunity().getOpportunityId());*/
			order.setAction(proposal.getAction());
			order.setAdvertiserDiscount(proposal.getAdvertiserDiscount());
			order.setAdvertiser(proposal.getAdvertiser());
			order.setAgency(proposal.getAgency());
			order.setAssignTo(proposal.getAssignTo());
			order.setCurrency(proposal.getCurrency());
			//order.setBillingSourceId(proposal.getBillingSource().getBillingSourceId());
			order.setSalesCategory(proposal.getSalesCategory());
			order.setAgencyMargin(proposal.getAgencyMargin());
			order.setMediaPlanner(proposal.getMediaPlanner());
			order.setBudget(proposal.getBudget());
			order.setCreatedBy(proposal.getSalesCategory().getSalesCatagoryId());
			order.setPercentageOfClose(proposal.getPercentageOfClose());
			order.setTerms(proposal.getTerms() != null ? proposal.getTerms(): null);
			order.setStatus(proposal.getStatus() != null ? proposal.getStatus() : null);
			order.setAgencyMargin(proposal.getAgencyMargin());
			order.setType(proposal.getType());
			order.setPlannerName(proposal.getPlannerName());
			order.setProposalDiscount(proposal.getProposalDiscount());
			order.setPricingModel(proposal.getPricingModel());
			order.setSalesPerson(proposal.getSalesPerson());
			order.setTrafficker(proposal.getTrafficker());
			order.setUpdated(new Date());

			Set<LineItems> lineItems = proposal.getLineItems();

			logger.debug("No of line items are " + lineItems.size());

			Set<OrderLineItems> orderItems = new HashSet<OrderLineItems>();

			for (LineItems items : lineItems) {
				OrderLineItems orderLineItems = new OrderLineItems();
				orderLineItems.setName(items.getName());
				orderLineItems.setLineItemId(items.getLineItemId());
				orderLineItems.setProduct(items.getProduct());
				orderLineItems.setProposal(items.getProposal());
				orderLineItems.setAvails(items.getAvails());
				orderLineItems.setCpms(items.getCpms());
				orderLineItems.setCreated(items.getCreated());
				orderLineItems.setCreatedBy(items.getCreatedBy());
				orderLineItems.setDeliveryImpressions(items.getDeliveryImpressions());
				orderLineItems.setFlightStartdate(items.getFlightStartdate());
				orderLineItems.setFlightEndDate(items.getFlightEndDate());
				orderLineItems.setDisplayCreatives(items.getDisplayCreatives());
				orderLineItems.setPriceAfterDiscount(items.getPriceAfterDiscount());
				orderLineItems.setPriority(items.getPriority());
				orderLineItems.setPriorityValues(Integer.parseInt(items.getCpms()));
				orderLineItems.setProposedCost(items.getProposedCost());
				orderLineItems.setRateType(items.getRateType());
				orderLineItems.setRotateCreatives(items.getRotateCreatives());
				orderLineItems.setSpecType(items.getSpecType());
				orderLineItems.setUpdated(items.getUpdated());
				orderLineItems.setUpdatedBy(items.getUpdatedBy());
				orderLineItems.setQuantity(items.getQuantity());
				Set<LineitemTarget> lineItemtargets = items.getTarget();
				
				Set<OrderLineitemTarget> orderLineitemTargets = new HashSet<OrderLineitemTarget>();
				for(LineitemTarget lineItemtarget: lineItemtargets){
					OrderLineitemTarget target = new OrderLineitemTarget();
					target.setAudienceTargetType(lineItemtarget.getAudienceTargetType());
					 Set<AudienceTargetValues> lineItemAudienceTargetValues = lineItemtarget.getAudienceTargetValues();
					 /*Set<OrderAudienceTargetValues> orderAudienceTargetValues = new HashSet<OrderAudienceTargetValues>();
					 
					 for(AudienceTargetValues lineItemAudienceTargetValue:lineItemAudienceTargetValues ){
						 OrderAudienceTargetValues orderAudienceTargetValue = new OrderAudienceTargetValues();
						 //orderAudienceTargetValue.setOrderTargetId(lineItemAudienceTargetValue.getId());
						 orderAudienceTargetValue.setId(lineItemAudienceTargetValue.getId());
						 orderAudienceTargetValue.setValue(lineItemAudienceTargetValue.getValue());
						 orderAudienceTargetValues.add(orderAudienceTargetValue);
					 }*/
					
					target.setAudienceTargetValues(lineItemAudienceTargetValues);
				}
				orderLineItems.setTarget(orderLineitemTargets);
				
				orderLineItems.setUpdated(new Date());

				/*
				 * Set orderLineItemTarget
				 */

				Set<OrderLineitemTarget> orderTarget = new HashSet<OrderLineitemTarget>();
				Set<LineitemTarget> lineItemTarget = items.getTarget();

				for (LineitemTarget lineTarget : lineItemTarget) {
					OrderLineitemTarget target = new OrderLineitemTarget();
					target.setAudienceTargetType(lineTarget.getAudienceTargetType());
					/** Setting up OrderAudienceTargetValues **/
					Set<AudienceTargetValues> targetValueSet = lineTarget.getAudienceTargetValues();
					Set<AudienceTargetValues> orderSet = new HashSet<AudienceTargetValues>();

					for (AudienceTargetValues itObj : targetValueSet) {
		/*				AudienceTargetValues obj = new AudienceTargetValues();
						obj.setId(itObj.getId());
						obj.setValue(itObj.getValue());
						obj.setStatus(itObj.isStatus());*/
						//obj.setOrderTargetId(itObj.getId());
						orderSet.add(itObj);
					}

					target.setAudienceTargetValues(orderSet);
					/** Done with Setting up OrderAudienceTargetValues **/

					orderTarget.add(target);

				}
				orderLineItems.setTarget(orderTarget);
				/** Finished setting orderLineItemTarget **/

				orderItems.add(orderLineItems);
			}

			order.setLineItems(orderItems);

			logger.warn("Before Saving order with proposal id " + proposalId);

			logger.warn("Order Entity to be saved is" + order.toString());
			return omsOrderRepository.save(order);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Proposal information", ex);
		}
	}

	@Override
	public Order updateOrder(Order order) {
		try {
			order.setUpdated(new Date());
			return omsOrderRepository.save(order);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Proposal information", ex);
		}
	}

	@Override
	public OrderLineItems updateOrderLineItem(OrderLineItems orderLineItem) {
		try {
			orderLineItem.setUpdated(new Date());
			return omsOrderLineItemRepository.save(orderLineItem);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Proposal information", ex);
		}
	}

	@Override
	public List<Order> findOrdersForVPZ() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		return omsOrderRepository.findOrdersForVPZ(false);
	}

	@Override
	public List<Order> getOrderList() {
		// TODO Auto-generated method stub
		return omsOrderRepository.findAll();
	}

	@Override
	public PaginationResponseVO<Order> searchOrder(SearchRequestVO searchRequest) {
		Page<Order> pageResponse = null;
		PaginationResponseVO<Order> response = null;
		try {
			pageResponse = omsOrderRepository.findAll(OrderSpecification.getOrderSpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Order>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		System.out.println("");
		return response;
	}

	@Override
	public Order getOrderById(Long orderId) {
		try {
			Order order = omsOrderRepository.findById(orderId);

			if (order != null) {
				return order;

			} else
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
						"Opportunity with {" + orderId + "} does not exist.");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Unable to fetch order id {" + orderId + "} ");
		}
	}

}
