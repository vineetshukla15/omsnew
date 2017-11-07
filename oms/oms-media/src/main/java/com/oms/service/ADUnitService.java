package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.ADUnit;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;


public interface ADUnitService {
	
	public List<ADUnit> getAllADUnit();
	
	public ADUnit getADUnit(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	public ADUnit addADUnit(ADUnit ADUnit) throws IllegalAccessException, InvocationTargetException, Exception;

	public ADUnit updateADUnit(ADUnit ADUnit) throws IllegalAccessException, InvocationTargetException, Exception;

	public void deleteADUnit(Long adUnitId) throws IllegalAccessException, InvocationTargetException, OMSSystemException;

	public PaginationResponseVO<ADUnit> searchAdUnit(SearchRequestVO searchRequest);

}
