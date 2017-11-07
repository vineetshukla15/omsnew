package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.HighLevelPermission;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.repository.HighLevelPermissionRepository;
import com.oms.service.HighLevelPermissionService;

@Service
public class HighLevelPermissionServiceImpl implements HighLevelPermissionService{
	
	final static Logger LOGGER = Logger.getLogger(HighLevelPermissionServiceImpl.class);
	
	
	@Autowired
	private HighLevelPermissionRepository highLevelPermissionRepository;

	@Override
	public List<HighLevelPermission> displayHighLevelPermissions() {
		List<HighLevelPermission> hlPermissionList = null;
		try {
			hlPermissionList = highLevelPermissionRepository.findByDeletedFalseOrderByPermissionNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Companies information",ex);
		}
		return hlPermissionList;
	}

}
