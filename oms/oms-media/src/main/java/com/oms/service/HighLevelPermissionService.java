package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.HighLevelPermission;

@Service
public interface HighLevelPermissionService {
	
		public List<HighLevelPermission> displayHighLevelPermissions();

}
