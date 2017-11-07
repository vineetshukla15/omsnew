package com.oms.service;

import java.util.List;

import org.tavant.api.auth.model.OMSUser;

public interface AuthenticationService {

	@SuppressWarnings("rawtypes")
	public List searchUserByLastName(String username);
	List<OMSUser> searchUserByFirstName(String firstName);
	List<OMSUser> searchUserByLoginName(String firstName);

}
