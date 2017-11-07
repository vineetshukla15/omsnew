package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Company;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface CompanyService {

	public Company addCompany(Company company);

	public Company updateCompany(Company company) throws OMSSystemException;

	public List<Company> listCompanies();

	public Company getCompanyById(Long companyID) throws OMSSystemException;

	public void removeCompany(Long companyID) throws OMSSystemException;

	public PaginationResponseVO<Company> searchCompany(SearchRequestVO searchRequest);

}
