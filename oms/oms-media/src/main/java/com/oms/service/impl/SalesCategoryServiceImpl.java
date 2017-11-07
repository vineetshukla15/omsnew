package com.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.oms.logging.annotation.Loggable;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.SalesCategory;
import com.oms.repository.SalesCategoryRepository;
import com.oms.service.SalesCategoryService;

@Service
public class SalesCategoryServiceImpl implements SalesCategoryService {

	@Autowired
	private SalesCategoryRepository salesCategoryRepository;

	@Loggable
	@Transactional
	public List<SalesCategory> getAllSalesCategory() {
		List<SalesCategory> salesCategoryList = null;
		try {
			salesCategoryList = salesCategoryRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Sales Category information", ex);
		}
		return salesCategoryList;
	}

}
