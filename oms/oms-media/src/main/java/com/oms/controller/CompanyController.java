package com.oms.controller;

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
import com.oms.model.Company;
import com.oms.service.CompanyService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/company",produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController extends BaseController {
	
	final static Logger logger = Logger.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	// to get all companies
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Company> listCompanies() {
		List<Company> companies = companyService.listCompanies();
		logger.info("List of Companies " + companies.toString());
		return companies;

	}
	
	// to add company
	@RequestMapping(method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_COMPANY)
	public @ResponseBody Company addCompany(@RequestBody Company company)throws OMSSystemException {
		company.setCreatedBy(findUserIDFromSecurityContext());
		company.setUpdatedBy(findUserIDFromSecurityContext());
		return companyService.addCompany(company);
	}
	
	// to update company
	@RequestMapping( method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_COMPANY)
	public @ResponseBody Company updateCompany(@RequestBody Company company) throws OMSSystemException {
		company.setUpdatedBy(findUserIDFromSecurityContext());
		return companyService.updateCompany(company);
	}
	
	// to delete company
	@Auditable(actionType = AuditingActionType.DELETE_COMPANY)
	@RequestMapping(value="/{companyId}"  ,method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCompany(@PathVariable("companyId") Long companyId) throws OMSSystemException {
		companyService.removeCompany(companyId);

	}
	
	// to get company by id
	@RequestMapping(value = "/{companyID}", method = RequestMethod.GET)
	public @ResponseBody Company getCompanyByID(@PathVariable long companyID) throws OMSSystemException {
		return companyService.getCompanyById(companyID);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Company> searchCompany(@RequestBody SearchRequestVO searchRequest ){
		return companyService.searchCompany(searchRequest);
	}
}
