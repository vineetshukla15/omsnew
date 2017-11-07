package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.CompanyStatus;
import com.oms.repository.CompanyStatusRepository;
import com.oms.service.CompanyStatusService;

@Service
public class CompanyStatusServiceImpl implements CompanyStatusService {
	
	final static Logger LOGGER = Logger.getLogger(CompanyStatusServiceImpl.class);
	
	@Autowired
	private CompanyStatusRepository companyStatusRepository;

	@Transactional
	public CompanyStatus addCompanyStatus(CompanyStatus companyStatus) {
		return companyStatusRepository.save(companyStatus);
	}

	@Transactional

	public CompanyStatus updateCompanyStatus(CompanyStatus companyStatus) throws OMSSystemException {
		if(companyStatus.getCompanyStatusId()!=null && Boolean.FALSE.equals(companyStatus.isDeleted())){
			companyStatusRepository.save(companyStatus);
			return companyStatus;
		}throw new OMSSystemException("Company Status with id {"+companyStatus.getCompanyStatusId()+"} does not exist");
		
	}

	@Transactional
	public List<CompanyStatus> listCompaniesStatus() {
		List<CompanyStatus> companyStatusList = null;
		try {
			companyStatusList = companyStatusRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Company Status information",ex);
		}
		return companyStatusList;
	}
	@Transactional
	@Override
	public CompanyStatus getCompanyStatusById(Long companyStatusID) throws OMSSystemException {
		if(companyStatusRepository.findById(companyStatusID)!=null){
			return companyStatusRepository.findById(companyStatusID);
		}

		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Company Status with id {"+companyStatusID+"} does not exist");
	}

	@Transactional
	@Override
	public void removeCompanyStatus(Long companyStatusId) throws OMSSystemException {
		CompanyStatus companyStatus = companyStatusRepository.findById(companyStatusId);
		if(companyStatus!=null && Boolean.FALSE.equals(companyStatus.isDeleted())){
			companyStatusRepository.softDelete(companyStatusId);
		} else {	
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Rate Card with Id {" + companyStatusId + "} does not exist or already deleted.");
		}
	}

}
