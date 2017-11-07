package com.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.service.OMSUserService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {

	@Autowired
	private OMSUserService userService;
	
	@RequestMapping(value = "/searchUserByRoleName/{roleName}", method = RequestMethod.GET)
	public List<OMSUser> getUserById( @PathVariable("roleName") String roleName) throws OMSSystemException {
		if(null == roleName || roleName.isEmpty()){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Role name is empty or null");
		}
		return userService.findByRoleName(roleName);
	}
}
