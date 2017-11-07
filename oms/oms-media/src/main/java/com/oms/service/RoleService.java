package com.oms.service;


import java.util.List;

import org.tavant.api.auth.model.Role;

import com.oms.exceptions.OMSSystemException;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;


public interface RoleService {
	
	public Role addRole(Role role)throws OMSSystemException;
	public Role updateRole(Role roleDTO) throws OMSSystemException;
	public List<Role> listRoles();
	public Role getRoleById(Long roleID) throws OMSSystemException;
	public void removeRole(Long roleId) throws OMSSystemException;
	public PaginationResponseVO<Role> searchRole(SearchRequestVO searchRequest);
}
