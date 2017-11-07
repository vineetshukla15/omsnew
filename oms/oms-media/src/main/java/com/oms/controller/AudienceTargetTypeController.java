package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.AudienceTargetType;
import com.oms.service.AudienceTargetTypeService;

/**
 * 
 * @author vikas.parashar
 *
 */
@RestController
@RequestMapping(value = "/audienceTargetType",produces = MediaType.APPLICATION_JSON_VALUE)
public class AudienceTargetTypeController extends BaseController {
	final static Logger LOGGER = Logger.getLogger(AudienceTargetTypeController.class);

	@Autowired
	private AudienceTargetTypeService audienceTargetTypeService;

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<AudienceTargetType> getAllAudienceTargetType()
			throws IllegalAccessException, InvocationTargetException {
		List<AudienceTargetType> audienceTargetTypes = audienceTargetTypeService.getAllAudienceTargetType();
		return audienceTargetTypes;
	}

	/**
	 * 
	 * @param audienceTargetTypeId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AudienceTargetTypeNotFoundException
	 */
	@RequestMapping(value = "/{audienceTargetTypeId}", method = RequestMethod.GET)
	public @ResponseBody AudienceTargetType getAudienceTargetType(@PathVariable Long audienceTargetTypeId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return audienceTargetTypeService.getAudienceTargetType(audienceTargetTypeId);
	}

	/**
	 * 
	 * @param audienceTargetType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_AUDIENCE_TARGET_TYPE)
	public @ResponseBody AudienceTargetType addAudienceTargetType(@RequestBody AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception {
		LOGGER.info("AudienceTargetType name" + audienceTargetType.getName());
		audienceTargetType.setCreatedBy(findUserIDFromSecurityContext());
		audienceTargetType.setUpdatedBy(findUserIDFromSecurityContext());
		return audienceTargetTypeService.addAudienceTargetType(audienceTargetType);
	}

	/**
	 * 
	 * @param audienceTargetType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@Auditable(actionType = AuditingActionType.UPDATE_AUDIENCE_TARGET_TYPE)
	@RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AudienceTargetType updateAudienceTargetType(@RequestBody AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception {
		LOGGER.info("rate Card info " + audienceTargetType.getTargetTypeId());
		audienceTargetType.setUpdatedBy(findUserIDFromSecurityContext());
		return audienceTargetTypeService.updateAudienceTargetType(audienceTargetType);
	}

	/**
	 * 
	 * @param audienceTargetType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AudienceTargetTypeNotFoundException
	 */
	@Auditable(actionType = AuditingActionType.DELETE_AUDIENCE_TARGET_TYPE)
	@RequestMapping(value="/{targetId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteAudienceTargetType(@PathVariable ("targetId") Long targetId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		audienceTargetTypeService.deleteAudienceTargetType(targetId);
	}

}