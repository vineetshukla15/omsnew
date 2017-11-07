package com.oms.controller;

import java.security.Principal;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.OMSUser;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.UserRoleVO;
import com.oms.service.AuthenticationService;
import com.oms.service.OMSUserService;
import com.oms.settings.errors.InvalidRequestException;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * This controller is responsible to manage the authentication system. Login -
 * Register - Forgot password - Account Confirmation
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController extends BaseController {

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Autowired
	private OMSUserService userService;
	
	@Autowired
	Environment env;

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/api/user/{username:.+}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OMSUser sampleGet(HttpServletResponse response, @PathVariable String username) {
		return userService.findByUserName(username);
	}

	/**
	 * Create a new user account
	 * 
	 * @param omsUser
	 *            user account
	 * @return created account as json
	 */
	@RequestMapping(value = "/api/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.CREATE_NEW_USER)
	public OMSUser register(@Valid @RequestBody OMSUser omsUser, BindingResult errors, Principal principal) {

		// Check if account is unique
		if (errors.hasErrors()) {
			throw new InvalidRequestException("Username already exists", errors);
		}

		return userService.saveUser(omsUser);

	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public List<OMSUser> getAllUsers() {
		return userService.getAllUser();

	}
	
	@RequestMapping(value = "/user/role/list", method = RequestMethod.GET)
	public Map<String, List<UserRoleVO>> getAllUsersWithRole() {
		return userService.getAllUsersWithRole();

	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.USER_REGISTRATION)
	public OMSUser addUser(@RequestBody OMSUser OMSUser) throws OMSSystemException {
		return userService.addUser(OMSUser); 
	}

	@Auditable(actionType = AuditingActionType.UPDATE_USER)
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public OMSUser updateUser(@RequestBody OMSUser userDTO) throws OMSSystemException {
		return userService.updateUser(userDTO);
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public OMSUser getUserById( @PathVariable("userId") long userId) throws OMSSystemException {
		return userService.findByUserId(userId);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_USER)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("userId") long userId) throws OMSSystemException {
		userService.deleteUser(userId);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public PaginationResponseVO<OMSUser> searchUser(@RequestBody SearchRequestVO searchRequest ){
		return userService.searchUser(searchRequest);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/searchUserByLastName/{lastName}", method = RequestMethod.GET)
	public List searchUserByLastName(@PathVariable String lastName)
			throws SignatureException {
		return authenticationService.searchUserByLastName(lastName);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/searchUserByFirstName/{firstName}", method = RequestMethod.GET)
	public List searchUserByFirstName(@PathVariable("firstName") String firstName) throws SignatureException {
		return authenticationService.searchUserByFirstName(firstName);

	}

	@RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
	public OMSUser getLoggedInUser() throws OMSSystemException {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findByUserName(username);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/searchUserByLastName/{lastName}", method = RequestMethod.POST)
	public List ldapUserByLastName(@PathVariable String lastName )throws SignatureException {
		return authenticationService.searchUserByLastName(lastName);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/searchUserByFirstName/{firstName}", method = RequestMethod.POST)
	public List ldapUserByFirstName(@PathVariable("firstName") String firstName) throws SignatureException {
		return authenticationService.searchUserByFirstName(firstName);

	}
}
