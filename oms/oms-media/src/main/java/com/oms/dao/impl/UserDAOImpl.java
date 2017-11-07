package com.oms.dao.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Role;

import com.oms.dao.UserDAO;
import com.oms.repository.OMSUserRepository;
import com.oms.repository.RoleRepository;

@Component("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	
	/**
	 * The Spring Data repository for Account entities.
	 */
	@Autowired
	private OMSUserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public OMSUser getUserById(long userId) {
		return userRepository.findByUserId(userId);	
	}

	@Override
	public OMSUser getUserByName(String username) {
		OMSUser account = userRepository.findByUsername(username);
		return account;
	}
	
	@Override
	public List<OMSUser> getUserByRoleName(String roleName) {
		return userRepository.findByRoleName(roleName);
	}

	@Override
	public OMSUser addUser(OMSUser user) {
		user.setCreated(new Date());
		Long roleId = null;
		if(user.getRole() != null && user.getRole().getRoleId() != null){
			roleId = user.getRole().getRoleId();
			Role role = roleRepository.findOne(roleId);
			user.setRole(role);
		}
		return userRepository.save(user);
	}

	@Override
	public List<OMSUser> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public OMSUser updateUser(OMSUser user) {
		OMSUser response=null;
		user.setUpdated(new Date());
		Long roleId = user.getRole().getRoleId();
		Role role = roleRepository.findOne(roleId);
		user.setRole(role);
		response= userRepository.save(user);
		return response;
	}

	@Override
	public void deleteUser(long userId) {
		OMSUser user =  userRepository.findByUserId(userId);
		user.setDeleted(true);
		userRepository.save(user);
	}

	@Override
	public List<OMSUser> getAllUser() {
		List<OMSUser> accounts = userRepository.findByDeletedFalse();
		return accounts;
	}

	@Override
	public Page<OMSUser> searchUser(String columnName, String searchText, Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "updated");
		Page<OMSUser> searchedUser = null;
		columnName = StringUtils.trimAllWhitespace(columnName);
		if (columnName.equalsIgnoreCase("firstName")) {
			searchedUser = userRepository.findByFirstNameIgnoreCaseContaining(searchText, pageRequest);
		} else if (columnName.equalsIgnoreCase("lastName")) {
			searchedUser = userRepository.findByLastNameIgnoreCaseContaining(searchText, pageRequest);
		} else if (columnName.equalsIgnoreCase("userName") || columnName.equalsIgnoreCase("loginName")) {
			searchedUser = userRepository.findByUsernameIgnoreCaseContaining(searchText, pageRequest);
		} else if (columnName.equalsIgnoreCase("emailId")) {
			searchedUser = userRepository.findByEmailIdIgnoreCaseContaining(searchText, pageRequest);
		} else if (columnName.equalsIgnoreCase("role")) {
			searchedUser = userRepository.findByRole("%" + searchText + "%", pageRequest);
		} else if (columnName.equalsIgnoreCase("active")) {
			boolean isActive = searchText.equalsIgnoreCase("true");
			searchedUser = userRepository.findUserByStatus(isActive, pageRequest);
		}
		return searchedUser;
	}

	@Override
	public OMSUser getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}




}
