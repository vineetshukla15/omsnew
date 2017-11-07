package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Order;
import com.oms.service.OMSOrderService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OmsOrderController extends BaseController {

	@Autowired
	OMSOrderService omsOrderService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Order createOrder(@RequestBody Long proposald)
			throws IllegalAccessException, InvocationTargetException, Exception {
		return omsOrderService.createOrder(proposald);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Order updateOrder(@RequestBody Order order)
			throws IllegalAccessException, InvocationTargetException, Exception {
		return omsOrderService.updateOrder(order);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Order> getOrderList() throws IllegalAccessException, InvocationTargetException, Exception {
		return omsOrderService.getOrderList();
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PaginationResponseVO<Order> searchOpportunity(@RequestBody SearchRequestVO searchRequest) {
		return omsOrderService.searchOrder(searchRequest);
	}

	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public @ResponseBody Order getOrderById(@PathVariable Long orderId) throws OMSSystemException {
		return omsOrderService.getOrderById(orderId);

	}

	//
	// @RequestMapping(value = "/{test}", method = RequestMethod.GET)
	// public Order getTest() {
	// /*List<String> strList = new ArrayList<String>();
	// strList.add("test");
	// return strList;*/
	// return omsOrderService.createOrder(new Long(284));
	// }
	//

	//
	// @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	// public Order listOrder(@RequestBody Long orderId)
	// throws IllegalAccessException, InvocationTargetException, Exception {
	//
	// return omsOrderService.getOrderById(orderId);
	// }

}
