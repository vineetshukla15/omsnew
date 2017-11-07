package com.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.Permissions;

import com.oms.service.PermissionService;

@RestController
@RequestMapping(value ="/permission", produces=MediaType.APPLICATION_JSON_VALUE)
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Permissions> displayPermissions() {
		 List<Permissions> permissions = permissionService.displayPermissions();
		return permissions;
	}
	

}
