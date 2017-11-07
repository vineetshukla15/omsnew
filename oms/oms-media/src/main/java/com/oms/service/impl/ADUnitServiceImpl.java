package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.ADUnit;
import com.oms.model.specification.ADUnitSpecification;
import com.oms.repository.ADUnitRepository;
import com.oms.service.ADUnitService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class ADUnitServiceImpl implements ADUnitService {

	final static Logger LOGGER = Logger.getLogger(ADUnitServiceImpl.class);

	@Autowired
	private ADUnitRepository adUnitRepository;

	@Override
	@Transactional
	public List<ADUnit> getAllADUnit() {
		List<ADUnit> adUnitResponse=null;
		try {
			adUnitResponse = adUnitRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive AddUnit information",ex);
		}
		return adUnitResponse;
	}

	@Override
	@Transactional
	public ADUnit getADUnit(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		if (adUnitRepository.findById(id) != null) {
			return adUnitRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Product Type with is {" + id + "} does not exist.");
	}

	@Override
	@Transactional
	public ADUnit addADUnit(ADUnit ADUnit)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		try {
			return adUnitRepository.save(ADUnit);
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Override
	@Transactional
	public ADUnit updateADUnit(ADUnit ADUnit) throws IllegalAccessException, InvocationTargetException, Exception {
		if (ADUnit.getAdUnitId() != null && Boolean.FALSE.equals(ADUnit.isDeleted())) {
			adUnitRepository.save(ADUnit);
			return ADUnit;
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Product Type with is {" + ADUnit.getAdUnitId() + "} does not exist.");
	}

	@Override
	@Transactional
	public void deleteADUnit(Long adUnitId)	throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		ADUnit adUnit = adUnitRepository.findById(adUnitId);
		if (adUnit != null && Boolean.FALSE.equals(adUnit.isDeleted())) {
			adUnitRepository.softDelete(adUnitId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Product Type with is {" + adUnitId + "} does not exist or already deleted.");
		}

	}

	@Override
	public PaginationResponseVO<ADUnit> searchAdUnit(SearchRequestVO searchRequest) {
		Page<ADUnit> pageResponse = null;
		PaginationResponseVO<ADUnit> response = null;
		try {
			pageResponse = adUnitRepository.findAll(ADUnitSpecification.getADUnitSpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<ADUnit>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		}catch (GenericJDBCException e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, "No Data Found",e);
		}
		catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found",e);
		}

		System.out.println("");
		return response;
	}

}
