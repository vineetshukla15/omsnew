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
import com.oms.model.Premium;
import com.oms.service.PremiumService;

/**
 * 
 * @author vikas.parashar
 *
 */
@RestController
@RequestMapping(value = "/premiums",produces = MediaType.APPLICATION_JSON_VALUE)
public class PremiumController extends BaseController {
	final static Logger LOGGER = Logger.getLogger(PremiumController.class);

	@Autowired
	private PremiumService premiumService;

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public @ResponseBody List<Premium> getAllPremium() throws IllegalAccessException, InvocationTargetException {
		List<Premium> premiums = premiumService.getAllPremium();
		return premiums;
	}

	/**
	 * 
	 * @param premiumId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws OMSSystemException
	 */
	@RequestMapping(value = "/{premiumId}", method = RequestMethod.GET)
	public @ResponseBody Premium getPremium(@PathVariable Long premiumId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return premiumService.getPremium(premiumId);
	}

	/**
	 * 
	 * @param premium
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_PREMIUM)
	public @ResponseBody Premium addPremium(@RequestBody Premium premium)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		LOGGER.info("Premium percentage" + premium.getPremiumPercentage());
		premium.setCreatedBy(findUserIDFromSecurityContext());
		premium.setUpdatedBy(findUserIDFromSecurityContext());
		return premiumService.addPremium(premium);
	}

	/**
	 * 
	 * @param premium
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_PREMIUM)
	public @ResponseBody Premium updatePremium(@RequestBody Premium premium)
			throws IllegalAccessException, InvocationTargetException, Exception {
		LOGGER.info("Premium info in update " + premium.getPremiumId());
		premium.setUpdatedBy(findUserIDFromSecurityContext());
		return premiumService.updatePremium(premium);
	}

	/**
	 * 
	 * @param premium
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws OMSSystemException
	 */
	@RequestMapping( value="/{premiumId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_PREMIUM)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletePremium(@PathVariable("premiumId") Long premiumId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		premiumService.deletePremium(premiumId);
	}

}