package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.Permissions;

@Service
public interface PermissionService {
	public List<Permissions> displayPermissions();

}
