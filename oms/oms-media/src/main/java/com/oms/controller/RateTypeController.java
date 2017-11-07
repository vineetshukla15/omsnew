package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.RateType;
import com.oms.service.RateTypeService;


@RestController
@RequestMapping(value = "/rateType",produces = MediaType.APPLICATION_JSON_VALUE)
public class RateTypeController extends BaseController{

		@Autowired
		private RateTypeService rateTypeService;

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public List<RateType> getAllRateType() {
			return rateTypeService.getAllRateType();
		}
		
		@RequestMapping(value = "/{rateTypeId}", method = RequestMethod.GET)
		public ResponseEntity<RateType> getRateType(@PathVariable Long rateTypeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			return new ResponseEntity<RateType>(rateTypeService.getRateType(rateTypeId), HttpStatus.OK);
		}

		@RequestMapping( method = RequestMethod.POST)
		@Auditable(actionType = AuditingActionType.ADD_RATE_TYPE)
		public RateType addNewRateType(RateType RateType) throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
			RateType.setCreatedBy(findUserIDFromSecurityContext());
			RateType.setUpdatedBy(findUserIDFromSecurityContext());
			return rateTypeService.addRateType(RateType);
		}

		@RequestMapping( method = RequestMethod.PUT)
		@Auditable(actionType = AuditingActionType.UPDATE_RATE_TYPE)
		public RateType updateRateType(RateType RateType) throws IllegalAccessException, InvocationTargetException, Exception {
			return rateTypeService.updateRateType(RateType);
		}

		@RequestMapping( value="/{rateTypeId}", method = RequestMethod.DELETE)
		@Auditable(actionType = AuditingActionType.DELETE_RATE_TYPE)
		@ResponseStatus(code = HttpStatus.NO_CONTENT)
		public void deleteRateType(@PathVariable("rateTypeId") Long rateTypeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			rateTypeService.deleteRateType(rateTypeId);
		}

	}


