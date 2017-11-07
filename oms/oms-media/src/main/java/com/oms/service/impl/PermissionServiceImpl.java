package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.api.auth.model.Permissions;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.repository.PermissionRepository;
import com.oms.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	final static Logger LOGGER = Logger.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Transactional
	public List<Permissions> displayPermissions() {
		List<Permissions> permissionList = null;
		try {
			permissionList = permissionRepository.findByDeletedFalseOrderByPermissionName();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Permisssions",ex);
		}
		return permissionList; 
		
	}

}
