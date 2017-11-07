package com.oms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.tavant.api.auth.model.OMSUser;

public interface UserDAO {
	
	OMSUser getUserById(long userId);
	
	OMSUser getUserByName(String name);
	
	List<OMSUser> getUserByRoleName(String roleName);
	
	OMSUser addUser(OMSUser user);

	List<OMSUser> getUserByEmail(String email);

	OMSUser updateUser(OMSUser user);

	void deleteUser(long userId);

	List<OMSUser> getAllUser();

	Page<OMSUser> searchUser(String columnName, String searchText,Integer pageNumber,Integer pageSize);
	
	OMSUser getUserByUsername(String username);

}
