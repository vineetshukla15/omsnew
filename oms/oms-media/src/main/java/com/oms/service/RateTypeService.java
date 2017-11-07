package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.RateType;

@Service
public interface RateTypeService {

	public List<RateType> getAllRateType();
	
	public RateType getRateType(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	public RateType addRateType(RateType RateType) throws IllegalAccessException, InvocationTargetException, Exception;

	public RateType updateRateType(RateType RateType) throws IllegalAccessException, InvocationTargetException, Exception;

	public void deleteRateType(Long rateTypeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException;

}
