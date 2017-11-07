package com.oms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.oms.logging.annotation.Loggable;

import com.oms.model.BillingSource;
import com.oms.service.BillingSourceService;

@RestController
@RequestMapping(value = "/billingSource", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillingSourceController extends BaseController {

	final static Logger logger = Logger.getLogger(BillingSourceController.class);

	@Autowired
	private BillingSourceService billingSourceService;

	@Loggable
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<BillingSource> getBillingSource() {
		List<BillingSource> billingSourcies = billingSourceService.getAllBillingSource();
		logger.info("List of Billing Source " + billingSourcies.toString());
		return billingSourcies;
	}
}
