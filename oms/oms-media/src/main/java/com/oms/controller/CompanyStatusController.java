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
import com.oms.model.CompanyStatus;
import com.oms.service.CompanyStatusService;

@RestController
@RequestMapping(value = "/companyStatus", produces = MediaType.APPLICATION_JSON_VALUE)

public class CompanyStatusController extends BaseController {

	final static Logger logger = Logger.getLogger(CompanyStatusController.class);

	@Autowired
	private CompanyStatusService companyStatusService;

	// to get all companiesStatus
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<CompanyStatus> listCompaniesStatus() {
		List<CompanyStatus> companiesStatus = companyStatusService.listCompaniesStatus();
		logger.info("List of Companies Status " + companiesStatus.toString());
		return companiesStatus;

	}

	// to add company status
	@RequestMapping(method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_COMPANYSTATUS)
	public @ResponseBody CompanyStatus addCompanyStatus(@RequestBody CompanyStatus companyStatus) {
		companyStatus.setCreatedBy(findUserIDFromSecurityContext());
		companyStatus.setUpdatedBy(findUserIDFromSecurityContext());
		return companyStatusService.addCompanyStatus(companyStatus);
	}

	// to update company status
	@RequestMapping(method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_COMPANYSTATUS)
	public @ResponseBody CompanyStatus updateCompanyStatus(@RequestBody CompanyStatus companyStatus) {
		companyStatus.setUpdatedBy(findUserIDFromSecurityContext());
		return companyStatusService.updateCompanyStatus(companyStatus);
	}

	// to delete company status
	@RequestMapping(value = "/{companyStatusId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_COMPANYSTATUS)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCompanyStatus(@PathVariable("companyStatusId") Long companyStatusId)
			throws OMSSystemException {
		companyStatusService.removeCompanyStatus(companyStatusId);
	}

	// to get company status by id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody CompanyStatus getCompanyStatusByID(@PathVariable long id) throws OMSSystemException {
		return companyStatusService.getCompanyStatusById(id);

	}

}
