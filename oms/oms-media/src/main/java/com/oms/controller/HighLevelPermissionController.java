package com.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.HighLevelPermission;

import com.oms.service.HighLevelPermissionService;

@RestController
@RequestMapping(value ="/hlpermission",produces = MediaType.APPLICATION_JSON_VALUE)
public class HighLevelPermissionController {
	
	
	@Autowired
	HighLevelPermissionService highLevelPermissionService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<HighLevelPermission> displayHighLevelPermissions() {
		List<HighLevelPermission> hlPermissions = highLevelPermissionService.displayHighLevelPermissions();
		return hlPermissions;
	}

}
