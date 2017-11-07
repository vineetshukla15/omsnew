package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
import com.oms.model.SeasonalDiscount;
import com.oms.service.SeasonalDiscountService;

/**
 * 
 * @author vikas.parashar
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SeasonalDiscountController extends BaseController {
	final static Logger logger = Logger.getLogger(SeasonalDiscountController.class);

	
	@Autowired
	private SeasonalDiscountService seasonalDiscountService;

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/seasonalDiscounts", method = RequestMethod.GET)
	public List<SeasonalDiscount> getAllSeasonalDiscount() {
		List<SeasonalDiscount> seasonalDiscounts = seasonalDiscountService.getAllSeasonalDiscount();
		return seasonalDiscounts;
	}

	/**
	 * 
	 * @param seasonalDiscountId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws OMSSystemException
	 */
	@RequestMapping(value = "/seasonalDiscount/{seasonalDiscountId}", method = RequestMethod.GET)
	public SeasonalDiscount getSeasonalDiscount(@PathVariable("seasonalDiscountId") Long seasonalDiscountId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return seasonalDiscountService.getSeasonalDiscount(seasonalDiscountId);
	}

	/**
	 * 
	 * @param seasonalDiscount
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@RequestMapping(value = "/seasonalDiscount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_NEW_SEASONAL_DISCOUNT)
	public @ResponseBody SeasonalDiscount addSeasonalDiscount(@RequestBody SeasonalDiscount seasonalDiscount)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		logger.info("SeasonalDiscount discocut" + seasonalDiscount.getDiscount());
		seasonalDiscount.setCreatedBy(findUserIDFromSecurityContext());
		seasonalDiscount.setCreated(new Date());
		seasonalDiscount.setUpdatedBy(findUserIDFromSecurityContext());
		seasonalDiscount.setUpdated(new Date());
		return seasonalDiscountService.addSeasonalDiscount(seasonalDiscount);
	}

	/**
	 * 
	 * @param seasonalDiscount
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	@RequestMapping(value = "/seasonalDiscount", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_SEASONAL_DISCOUNT)
	public @ResponseBody SeasonalDiscount updateSeasonalDiscount(@RequestBody SeasonalDiscount seasonalDiscount)
			throws IllegalAccessException, InvocationTargetException, Exception {
		logger.info("SeasonalDiscount update with id" + seasonalDiscount.getId());
		seasonalDiscount.setUpdatedBy(findUserIDFromSecurityContext());
		return seasonalDiscountService.updateSeasonalDiscount(seasonalDiscount);
	}

	/**
	 * 
	 * @param seasonalDiscoun
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws OMSSystemException
	 */
	@RequestMapping(value = "/seasonalDiscount/{id}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_SEASONAL_DISCOUNT)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteSeasonalDiscount(@PathVariable("id") Long seasonalDiscountId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		seasonalDiscountService.deleteSeasonalDiscount(seasonalDiscountId);
	}

}