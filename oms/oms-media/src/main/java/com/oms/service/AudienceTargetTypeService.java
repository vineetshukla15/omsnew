package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.AudienceTargetType;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public interface AudienceTargetTypeService {

	/**
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<AudienceTargetType> getAllAudienceTargetType() throws IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws AudienceTargetTypeNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public AudienceTargetType getAudienceTargetType(Long id)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * @param audienceTargetType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public AudienceTargetType addAudienceTargetType(AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param audienceTargetType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public AudienceTargetType updateAudienceTargetType(AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception;

	/**
	 * 
	 * @param targetId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AudienceTargetTypeNotFoundException
	 */
	public void deleteAudienceTargetType(Long targetId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException;

}
