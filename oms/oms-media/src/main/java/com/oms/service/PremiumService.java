package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Premium;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public interface PremiumService {

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<Premium> getAllPremium() throws IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PremiumNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Premium getPremium(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * @param premium
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public Premium addPremium(Premium premium) throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param premium
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public Premium updatePremium(Premium premium) throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param premiumId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws PremiumNotFoundException
	 */
	public void deletePremium(Long premiumId) throws IllegalAccessException, InvocationTargetException, OMSSystemException;

}
