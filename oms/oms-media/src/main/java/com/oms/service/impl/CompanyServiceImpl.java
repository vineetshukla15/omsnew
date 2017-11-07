package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Company;
import com.oms.model.specification.CompanySpecification;
import com.oms.repository.CompanyRepository;
import com.oms.service.CompanyService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class CompanyServiceImpl implements CompanyService {

	final static Logger LOGGER = Logger.getLogger(CompanyServiceImpl.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Transactional
	public Company addCompany(Company company) throws OMSSystemException {
		try {
			Company existingCompany = companyRepository.findCompanyByName(company.getName().toLowerCase(), false);
			if(null != existingCompany){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Comapny with name " + company.getName() + " already exist");
			}
			return companyRepository.save(company);
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public List<Company> listCompanies() {
		List<Company> companyList = null;
		try {
			companyList = companyRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Companies information",ex);
		}
		return companyList;
	}

	@Transactional
	public Company updateCompany(Company company) throws OMSSystemException {
		try {
			Company existingCompany = companyRepository.findCompanyByName(company.getName().toLowerCase(), false);
			if(null != existingCompany && company.getName().equals(existingCompany.getName()) && !company.getCompanyId().equals(existingCompany.getCompanyId())){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Company with name " + company.getName() + " already exist");
			}
			if (company.getCompanyId() != null && Boolean.FALSE.equals(company.isDeleted())) {
				return companyRepository.save(company);
			}
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Company with id {" + company.getCompanyId() + "} does not exist.");
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, ex.getMessage());
		}

	}

	@Transactional
	public Company getCompanyById(Long companyID) throws OMSSystemException {
		if (companyRepository.findById(companyID) != null) {
			return companyRepository.findById(companyID);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Company with id {" + companyID + "} does not exist");
	}

	@Transactional
	@Override
	public void removeCompany(Long companyID) throws OMSSystemException {
		Company company = companyRepository.findById(companyID);
		if (company != null && Boolean.FALSE.equals(company.isDeleted())) {
			companyRepository.softDelete(companyID);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Company with Id {" + companyID + "} does not exist or already deleted");
		}
	}

	@Override
	public PaginationResponseVO<Company> searchCompany(SearchRequestVO searchRequest) {
		Page<Company> pageResponse = null;
		PaginationResponseVO<Company> response = null;
		try {
			pageResponse = companyRepository.findAll(CompanySpecification.getCompanySpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Company>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		System.out.println("");
		return response;
	}

}
