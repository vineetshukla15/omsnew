package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.CompanyStatus;

@Service
public interface CompanyStatusService {
	public CompanyStatus addCompanyStatus(CompanyStatus companyStatus);

	public CompanyStatus updateCompanyStatus(CompanyStatus companyStatus) throws OMSSystemException;

	public List<CompanyStatus> listCompaniesStatus();

	public CompanyStatus getCompanyStatusById(Long companyStatusID) throws OMSSystemException;

	public void removeCompanyStatus(Long companyStatusId) throws OMSSystemException;

}
