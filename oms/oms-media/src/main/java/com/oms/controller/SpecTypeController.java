package com.oms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oms.model.SpecType;
import com.oms.service.SpecTypeService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecTypeController extends BaseController {

	final static Logger logger = Logger.getLogger(CompanyController.class);

	@Autowired
	private SpecTypeService specTypeService;

	// to get all specTypes
	@RequestMapping(value = "/specTypes", method = RequestMethod.GET)
	public List<SpecType> listSpecTypes() {
		List<SpecType> specTypes = specTypeService.listSpecType();
		logger.info("List of Spec Types " + specTypes.toString());
		return specTypes;

	}

}
