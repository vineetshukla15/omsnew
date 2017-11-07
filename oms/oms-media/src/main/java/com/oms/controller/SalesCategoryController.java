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

import com.oms.model.SalesCategory;
import com.oms.service.SalesCategoryService;

@RestController
@RequestMapping(value = "/salesCategory", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesCategoryController extends BaseController {

	final static Logger logger = Logger.getLogger(SalesCategoryController.class);

	@Autowired
	private SalesCategoryService salesCategoryService;

	@Loggable
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<SalesCategory> getSalesCategory() {
		List<SalesCategory> salesCategories = salesCategoryService.getAllSalesCategory();
		logger.info("List of Sales Category " + salesCategories.toString());
		return salesCategories;
	}
}
