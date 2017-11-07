package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.SeasonalDiscount;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public interface SeasonalDiscountService {

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<SeasonalDiscount> getAllSeasonalDiscount();
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SeasonalDiscountNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public SeasonalDiscount getSeasonalDiscount(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * @param seasonalDiscount
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public SeasonalDiscount addSeasonalDiscount(SeasonalDiscount seasonalDiscount) throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param seasonalDiscount
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public SeasonalDiscount updateSeasonalDiscount(SeasonalDiscount seasonalDiscount) throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param seasonalDiscountId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SeasonalDiscountNotFoundException
	 */
	public void deleteSeasonalDiscount(Long seasonalDiscountId) throws IllegalAccessException, InvocationTargetException,  OMSSystemException;

}
