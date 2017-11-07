package com.oms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.ads.dfp.axis.utils.v201705.DateTimes;
import com.google.api.ads.dfp.axis.v201705.BrowserTargeting;
import com.google.api.ads.dfp.axis.v201705.CostType;
import com.google.api.ads.dfp.axis.v201705.CreativePlaceholder;
import com.google.api.ads.dfp.axis.v201705.CreativeRotationType;
import com.google.api.ads.dfp.axis.v201705.GeoTargeting;
import com.google.api.ads.dfp.axis.v201705.Goal;
import com.google.api.ads.dfp.axis.v201705.GoalType;
import com.google.api.ads.dfp.axis.v201705.LineItem;
import com.google.api.ads.dfp.axis.v201705.LineItemType;
import com.google.api.ads.dfp.axis.v201705.Location;
import com.google.api.ads.dfp.axis.v201705.Money;
import com.google.api.ads.dfp.axis.v201705.Size;
import com.google.api.ads.dfp.axis.v201705.StartDateTimeType;
import com.google.api.ads.dfp.axis.v201705.Targeting;
import com.google.api.ads.dfp.axis.v201705.Technology;
import com.google.api.ads.dfp.axis.v201705.TechnologyTargeting;
import com.google.api.ads.dfp.axis.v201705.UnitType;
import com.oms.dfp.service.DFPOrderService;
import com.oms.model.Order;
import com.oms.model.OrderLineItems;
import com.oms.service.AudienceTargetTypeService;
import com.oms.service.DFPService;

@Service
public class DFPServiceImpl implements DFPService {

	@Autowired
	DFPOrderService dfpOrderService;
	
	// @Autowired
	// @Qualifier("getAllBrowser")
	// private Map<String, String> getAllBrowser;

	@Autowired
	AudienceTargetTypeService audienceTargetTypeService;

	@Override
	public List<com.google.api.ads.dfp.axis.v201705.Order> createDFPOrder(Order order, boolean createLineItems) {
//		long traffickerId = order.getTrafficker().getId();
//		long advertiserId = order.getAdvertiser().getCompanyId();
		// List<Long> dfpOrderids = new ArrayList<Long>();

		com.google.api.ads.dfp.axis.v201705.Order dfpOrder = new com.google.api.ads.dfp.axis.v201705.Order();
		dfpOrder.setName(order.getName());
		dfpOrder.setAdvertiserId(29766864L); //
		dfpOrder.setTraffickerId(115818864L);
		List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrdeList = dfpOrderService.createDFPOrder(dfpOrder);

		
		if (createLineItems){
			createLineItems(order, dfpOrdeList);
		}
		
		return dfpOrdeList;

	}
	
	@Override
	public Map<com.google.api.ads.dfp.axis.v201705.Order,Set<LineItem>> createDFPOrderWithLineItems(Order order){
		
		Map<com.google.api.ads.dfp.axis.v201705.Order,Set<LineItem>> orderMap = new HashMap<>();
		com.google.api.ads.dfp.axis.v201705.Order dfpOrder = new com.google.api.ads.dfp.axis.v201705.Order();
		dfpOrder.setName(order.getName());
		dfpOrder.setAdvertiserId(29766864L); //
		dfpOrder.setTraffickerId(115818864L);
		List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrdeList = dfpOrderService.createDFPOrder(dfpOrder);
		dfpOrdeList.forEach(dfpOrderobj->{
			orderMap.put(dfpOrderobj, createLineItemsForSingleOrder(order, dfpOrderobj));
		});
		
		
		return orderMap;
		
	}
	
	private Set<LineItem> createLineItemsForSingleOrder(Order order,com.google.api.ads.dfp.axis.v201705.Order dfpOrderObj){
		
		List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrderList= new ArrayList<com.google.api.ads.dfp.axis.v201705.Order>();
		dfpOrderList.add(dfpOrderObj);		
		return createLineItems(order,dfpOrderList);
	}

	@Override
	public Set<LineItem> createLineItems(Order order, List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrderList) {

		// for (com.google.api.ads.dfp.axis.v201705.Order dfporder :
		// dfpOrderList) {
		// orderIdList.add(dfporder.getId());
		// }
		Set<LineItem> dfpLineItems = new HashSet<LineItem>();
		if (order.getLineItems().size() > 0) {
			Set<LineItem> lineItems = mapLineItem(order, dfpOrderList);			
			dfpLineItems = dfpOrderService.createDFPLineItem(lineItems);
		}
		return dfpLineItems;
	}

	private Set<LineItem> mapLineItem(Order order, List<com.google.api.ads.dfp.axis.v201705.Order> dfpOrderObj) {

		Set<OrderLineItems> lineItems = order.getLineItems();

		Set<LineItem> dfpLineItemSet = new HashSet<LineItem>();

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		for (com.google.api.ads.dfp.axis.v201705.Order dfpOrder : dfpOrderObj) {
			for (OrderLineItems orderLineItems : lineItems) {
				LineItem dfpLineItem = new LineItem();
				dfpLineItem.setOrderId(dfpOrder.getId());
				dfpLineItem.setName(orderLineItems.getName());
				dfpLineItem.setAllowOverbook(true);
				dfpLineItem.setLineItemType(LineItemType.STANDARD);
				dfpLineItem.setPriority(orderLineItems.getPriorityValues());
				// set Targetting
				setTargetting(dfpLineItem, orderLineItems);
				// Set the length of the line item to run
				Date startDate = orderLineItems.getFlightStartdate();
				String startDateTime = sm.format(startDate);
				
				dfpLineItem.setStartDateTimeType(StartDateTimeType.IMMEDIATELY);
				//dfpLineItem.setStartDateTime(DateTimes.toDateTimeWithTimeZone(startDateTime));

				Date endDate = orderLineItems.getFlightEndDate();
				String endDateTime = sm.format(endDate);
				dfpLineItem.setEndDateTime(DateTimes.toDateTimeWithTimeZone(endDateTime));
				// set the line item cost
				//setDfpCostType(dfpLineItem, orderLineItems.getRateType().getName());
				// Set the cost per unit to $2.
				
				dfpLineItem.setCostType(CostType.CPM);
				dfpLineItem.setCostPerUnit(new Money("USD", 2000000L));

			    // Set the number of units bought to 500,000 so that the budget is
			    // $1,000.
			    Goal goal = new Goal();
			    goal.setGoalType(GoalType.LIFETIME);
			    goal.setUnits(500000L);
			    goal.setUnitType(UnitType.IMPRESSIONS);
			    dfpLineItem.setPrimaryGoal(goal);
			    
			 // Set the size of creatives that can be associated with this line item.
			    dfpLineItem.setCreativeRotationType(CreativeRotationType.EVEN);

			    dfpLineItem.setCreativePlaceholders(new CreativePlaceholder[] {setCreative()});

				dfpLineItemSet.add(dfpLineItem);
			}
			
		}

		return dfpLineItemSet;

	}

	private LineItem setDfpCostType(LineItem lineItem, String rateTypeName) {
		try {
			if (rateTypeName.equals("CPM"))
				lineItem.setCostType(CostType.CPM);
			if (rateTypeName.equals("CPA"))
				lineItem.setCostType(CostType.CPA);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return lineItem;
	}

	private void setTargetting(LineItem lineItem, OrderLineItems omsLineItem) {

		
		

		/*Set<OrderLineitemTarget> omsTarget = omsLineItem.getTarget();
		for (OrderLineitemTarget orderTarget : omsTarget) {

			if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Age")) {

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Gender")) {

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("States")) {
				// TODO
				stateLocation.setId(2840L);

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Countries")) {
				// TODO
				countryLocation.setId(2840L);

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Device")) {

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Browser")) {

				// Create browser targeting.

				browserTargeting.setIsTargeted(true);
				// TODO

				orderTarget.getAudienceTargetType().getTargetTypeId();
				browserTechnology.setId(500072L);
				browserTargeting.setBrowsers(new Technology[] { browserTechnology });
				technologyTargeting.setBrowserTargeting(browserTargeting);

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("OS")) {

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Frequency cap")) {

			} else if (orderTarget.getAudienceTargetType().getName().equalsIgnoreCase("Zip codes")) {
				// TODO
				postalCodeLocation.setId(9000093L);
			}
		}
	*/

	   

	    Targeting targeting = new Targeting();
		
	    targeting.setGeoTargeting(setGeoTargetting()); 
	    targeting.setTechnologyTargeting(setTechTargetting());		
		
		lineItem.setTargeting(targeting);

	}

	private CreativePlaceholder setCreative() {

		// Set the creative rotation type to even.
				//Set<Creative> creatives = omsLineItem.getProduct().getCreatives();

		// Create creative placeholder size.
		/*for (Creative creative : creatives) {
			Size size = new Size();
			size.setWidth(creative.getWidth1().intValue());
			size.setHeight(creative.getWidth1().intValue());
			size.setIsAspectRatio(false);
			// Create the creative placeholder.
			CreativePlaceholder creativePlaceholder = new CreativePlaceholder();
			creativePlaceholder.setSize(size);
		}*/
		// Set the size of creatives that can be associated with this line item.
		// lineItem.setCreativePlaceholders(new CreativePlaceholder[] {
		// creativePlaceholder });
		
		Size size = new Size();
	    size.setWidth(300);
	    size.setHeight(250);
	    size.setIsAspectRatio(false);

	    // Create the creative placeholder.
	    CreativePlaceholder creativePlaceholder = new CreativePlaceholder();
	    creativePlaceholder.setSize(size);
		return creativePlaceholder;
	}
	
	private GeoTargeting setGeoTargetting(){
		// Create geographical targeting.
	    GeoTargeting geoTargeting = new GeoTargeting();

	    // Include the US, Quebec, Canada, and the B3P Canada postal code.
	    // To determine what other geographic criteria exists,
	    // run GetGeoTargets.java.
	    Location countryLocation = new Location();
	    countryLocation.setId(2840L);

	    Location regionLocation = new Location();
	    regionLocation.setId(20133L);

	    Location postalCodeLocation = new Location();
	    postalCodeLocation.setId(9000093L);

	    geoTargeting.setTargetedLocations(new Location[] {countryLocation, regionLocation,
	        postalCodeLocation});

	    // Exclude Chicago and the New York metro area.
	    // To determine what other geographic criteria exists, run
	    // GetGeoTargets.java.
	    Location cityLocation = new Location();
	    cityLocation.setId(1016367L);

	    Location metroLocation = new Location();
	    metroLocation.setId(200501L);
	    geoTargeting.setExcludedLocations(new Location[] {cityLocation, metroLocation});
	    
	    return geoTargeting;
	}
	
	private TechnologyTargeting setTechTargetting(){
		
		TechnologyTargeting technologyTargeting = new TechnologyTargeting();
		
		Technology browserTechnology = new Technology();
		BrowserTargeting browserTargeting = new BrowserTargeting();
		browserTargeting.setIsTargeted(true);
		// TODO		
		browserTechnology.setId(500072L);
		browserTargeting.setBrowsers(new Technology[] { browserTechnology });
		technologyTargeting.setBrowserTargeting(browserTargeting);
		
		return technologyTargeting;
	}

}
