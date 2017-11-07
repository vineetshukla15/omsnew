package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.RateType;
import com.oms.repository.RateTypeRepository;
import com.oms.service.RateTypeService;
//import com.oms.hotstar.converter.RateTypeConverter;

@Service
public class RateTypeServiceImpl implements RateTypeService{
	
	final static Logger LOGGER = Logger.getLogger(RateTypeServiceImpl.class);
	
	@Autowired
	private RateTypeRepository rateTypeRepository;

	@Override
	@Transactional
	public List<RateType> getAllRateType() {
		List<RateType> rateTypes = null;
		try {
			rateTypes = rateTypeRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive RateType information",ex);
		}
		return rateTypes;
	}

	@Override
	@Transactional
	public RateType getRateType(Long id) throws OMSSystemException{
		if (rateTypeRepository.findById(id) != null) {
			return rateTypeRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,"Rate Type with is {" + id + "} does not exist.");
	}
	@Override
	@Transactional
	public RateType addRateType(RateType rateType)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		try{
			return rateTypeRepository.save(rateType);
		}catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Override
	@Transactional
	public RateType updateRateType(RateType rateType)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		if(rateType.getRateTypeId()!=null && Boolean.FALSE.equals(rateType.isDeleted())){
			rateTypeRepository.save(rateType);
			return rateType;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Rate Type with {" + rateType.getRateTypeId() + "} does not exist.");
	}

	@Override
	@Transactional
	public void deleteRateType(Long rateTypeId)	throws OMSSystemException{
		RateType rateType = rateTypeRepository.findById(rateTypeId);
		if (rateType != null && Boolean.FALSE.equals(rateType.isDeleted())) {
			rateTypeRepository.softDelete(rateTypeId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,"Rate Type with is {" + rateTypeId+ "} does not exist or already deleted.");
		}
	}

}
