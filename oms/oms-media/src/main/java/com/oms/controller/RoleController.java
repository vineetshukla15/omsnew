package com.oms.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.Role;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.service.RoleService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController extends BaseController {

	final static Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;

	// to get all roles
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Role> listRoles() {
		List<Role> roles = roleService.listRoles();
		logger.info("List of Roles " + roles.toString());
		return roles;
	}

	// to create new Role
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_NEW_ROLE)
	public @ResponseBody Role addRole(@RequestBody Role role)throws OMSSystemException{
		role.setCreatedBy(findUserIDFromSecurityContext());
		role.setCreated(new Date());
		role.setUpdatedBy(findUserIDFromSecurityContext());
		role.setUpdated(new Date());
		return roleService.addRole(role);

	}

	// to delete roles
	@RequestMapping(value= "/{roleId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_ROLE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeRole(@PathVariable("roleId") Long roleId) throws OMSSystemException {
		roleService.removeRole(roleId);

	}

	// to get Role by ID
	@RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
	public @ResponseBody Role getRole(@PathVariable long roleID) throws OMSSystemException {
		return roleService.getRoleById(roleID);
	}

	// to update Role
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_ROLE)
	public @ResponseBody Role updateRole(@RequestBody Role role) throws OMSSystemException {
		role.setUpdatedBy(findUserIDFromSecurityContext());
		role.setUpdated(new Date());
		return roleService.updateRole(role);

	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Role> searchUser(@RequestBody SearchRequestVO searchRequest ){
		return roleService.searchRole(searchRequest);
	}
}
