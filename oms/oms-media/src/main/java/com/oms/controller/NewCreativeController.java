package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.NewCreative;
import com.oms.service.NewCreativeService;

@RestController
@RequestMapping(value = "/newcreative", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewCreativeController extends BaseController { 
	
	@Autowired
	NewCreativeService newCreativeService;
	
	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)	
	public  NewCreative addNewCreative(@RequestBody NewCreative newCreative)
			throws IllegalAccessException, InvocationTargetException, Exception {
		return newCreativeService.addCreative(newCreative, findUserIDFromSecurityContext());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public  List<NewCreative> getAllNewCreatives() {
		return newCreativeService.getAllNewCreatives();
	}

	@RequestMapping(value = "/{newcreativeid}", method = RequestMethod.GET)
	public  NewCreative getNewCreative(@PathVariable Long creative1Id) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return newCreativeService.getNewCreativeById(creative1Id);
	}
	
}
