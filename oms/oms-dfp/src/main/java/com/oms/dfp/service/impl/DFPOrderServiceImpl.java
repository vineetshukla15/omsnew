package com.oms.dfp.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import com.oms.dfp.service.DFPOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.email.exceptions.OMSSystemException;
import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.v201705.AdUnitTargeting;
import com.google.api.ads.dfp.axis.v201705.ApiError;
import com.google.api.ads.dfp.axis.v201705.ApiException;
import com.google.api.ads.dfp.axis.v201705.InventoryTargeting;
import com.google.api.ads.dfp.axis.v201705.LineItem;
import com.google.api.ads.dfp.axis.v201705.LineItemServiceInterface;
import com.google.api.ads.dfp.axis.v201705.NetworkServiceInterface;
import com.google.api.ads.dfp.axis.v201705.Order;
import com.google.api.ads.dfp.axis.v201705.OrderServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

@Service
public class DFPOrderServiceImpl implements DFPOrderService {

	final static Logger logger = Logger.getLogger(DFPOrderServiceImpl.class);

	@Autowired
	DfpSession dfpsession;

	@Override
	public List<Order> createDFPOrder(Order dfpOrders) {
		DfpServices dfpServices = new DfpServices();
		OrderServiceInterface orderService = dfpServices.get(dfpsession, OrderServiceInterface.class);
		List<Order> orderList = new ArrayList<Order>();
		try {
			Order[] orders = orderService.createOrders(new Order[] { dfpOrders });
			orderList = Arrays.asList(orders);
		} catch(ApiException e)
	    {
			logger.error(e.getMessage());
	        ApiError[] apiErrors = e.getErrors();
	        for (ApiError apiError : apiErrors) {
	          StringBuilder errorMessage = new StringBuilder();
	          errorMessage.append(String.format(
	              "There was an error of type APIException, on the field '%s',"
	              + "caused by an invalid "
	              + "value '%s', with the error message '%s' ",
//	              + "with the reason '%s' ",
	              apiError.getFieldPath(),
	              apiError.getTrigger(), apiError.getErrorString()));
	          
	          logger.error("Inside first catch block ApiException.");
	          logger.error(errorMessage.toString());
	        }
	    }catch(Exception ex){
	    	logger.error("DFPOrderServiceImpl:createDFPOrder -> Failed to create createDFPOrder");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			throw new OMSSystemException("Unable to create DFPorder information");
	    }
		
		return orderList;
	}

	@Override
	public Set<LineItem> createDFPLineItem(Set<LineItem> lineitems) {
		
		// Create the line item on the server.
		DfpServices dfpServices = new DfpServices();
		LineItemServiceInterface lineItemService = dfpServices.get(dfpsession, LineItemServiceInterface.class);
		NetworkServiceInterface networkService =
		        dfpServices.get(dfpsession, NetworkServiceInterface.class);
		String rootAdUnitId=null;
		try {
			rootAdUnitId = networkService.getCurrentNetwork().getEffectiveRootAdUnitId();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create inventory targeting.
	    InventoryTargeting inventoryTargeting = new InventoryTargeting();

	    // Create ad unit targeting for the root ad unit (i.e. the whole network).
	    AdUnitTargeting adUnitTargeting = new AdUnitTargeting();
	    adUnitTargeting.setAdUnitId(rootAdUnitId);
	    adUnitTargeting.setIncludeDescendants(true);

	    inventoryTargeting.setTargetedAdUnits(new AdUnitTargeting[] {adUnitTargeting});
	    
	    lineitems.forEach(lineItem->{
	    	lineItem.getTargeting().setInventoryTargeting(inventoryTargeting);
	    });
	    
		Set<LineItem> lineItemsList = new HashSet<LineItem>();
		try {
			LineItem[] lineItems = lineItemService.createLineItems(lineitems.toArray(new LineItem[lineitems.size()]));
			lineItemsList = new HashSet(Arrays.asList(lineitems.toArray()));
		} catch (RemoteException ex) {
			logger.error("DFPOrderServiceImpl:createDFPLineItem -> Failed to create createDFPLineItem");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			throw new OMSSystemException("Unable to create lineitems information");

		}
		return lineItemsList;
	}

}
