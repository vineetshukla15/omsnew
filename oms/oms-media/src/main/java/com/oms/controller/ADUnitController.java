package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.oms.model.ADUnit;
import com.oms.service.ADUnitService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/adUnit",produces = MediaType.APPLICATION_JSON_VALUE)
public class ADUnitController extends BaseController{

		@Autowired
		private ADUnitService adUnitService;

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public List<ADUnit> getAllADUnit() throws IllegalAccessException, InvocationTargetException {
			return adUnitService.getAllADUnit();
		}
		
		@RequestMapping(value = "/{adUnitId}", method = RequestMethod.GET)
		public @ResponseBody ResponseEntity<ADUnit> getADUnit(@PathVariable Long adUnitId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			return new ResponseEntity<ADUnit>(adUnitService.getADUnit(adUnitId), HttpStatus.OK);
		}

		@RequestMapping( method = RequestMethod.POST)
		@Auditable(actionType = AuditingActionType.ADD_NEW_ADUNIT)
		public @ResponseBody ADUnit addNewADUnit(@RequestBody ADUnit ADUnit) throws IllegalAccessException, InvocationTargetException, Exception {
			ADUnit.setCreatedBy(findUserIDFromSecurityContext());
			ADUnit.setUpdatedBy(findUserIDFromSecurityContext());
			return adUnitService.addADUnit(ADUnit);
		}

		@RequestMapping( method = RequestMethod.PUT)
		@Auditable(actionType = AuditingActionType.UPDATE_ADUNIT)
		public @ResponseBody ADUnit updateADUnit(@RequestBody ADUnit ADUnit) throws IllegalAccessException, InvocationTargetException, Exception {
			ADUnit.setUpdatedBy(findUserIDFromSecurityContext());
			ADUnit.setUpdated(new Date());
			return adUnitService.updateADUnit(ADUnit);
		}

		@RequestMapping( value = "/{adUnitId}",method = RequestMethod.DELETE)
		@Auditable(actionType = AuditingActionType.DELETE_ADUNIT)
		@ResponseStatus(value = HttpStatus.NO_CONTENT)
		public void deleteADUnit(@PathVariable("adUnitId") Long adUnitId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			adUnitService.deleteADUnit(adUnitId);
		}
		
		@RequestMapping(value = "/list", method = RequestMethod.POST)
		public PaginationResponseVO<ADUnit> searchADUnit(@RequestBody SearchRequestVO searchRequest ){
			return adUnitService.searchAdUnit(searchRequest);
		}
	}


