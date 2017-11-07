package com.oms.service;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.UserRoleVO;
import com.oms.model.VerificationToken;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;
import com.oms.viewobjects.UserDetailsVO;

public interface OMSUserService {

    Collection<OMSUser> findAll();

    OMSUser findByUserId(long userId);
    
    OMSUser findByUserName(String userName);
    
    List<OMSUser> findByRoleName(String roleName) throws OMSSystemException;
    
    OMSUser saveUser(OMSUser account);

	List<OMSUser> getAllUser();

	OMSUser addUser(OMSUser userDTO) throws OMSSystemException;

	OMSUser updateUser(OMSUser userDTO);

	void deleteUser(long userId);

	//PaginationResponseVO<OMSUser, PageAttributesVO> searchUser(String attributeName, String searchText,Integer pangNo,Integer pageSize, String sortBy, Direction sortDirection);
	
	VerificationToken createVerificationTokenForUser(OMSUser user, String token);

	VerificationToken generateNewVerificationToken(String existingToken);

	OMSUser getOMSUserByToken(String token);

	String generateQRUrl(OMSUser user) throws UnsupportedEncodingException;

	UserDetailsVO validateVerificationToken(String token);

	OMSUser registerNewOMSUserAccount(OMSUser accountDto) throws OMSSystemException;

	void resendVerificationMail(String userEmail);

	void updatePassword(OMSUser user) throws OMSSystemException;

	PaginationResponseVO<OMSUser> searchUser(SearchRequestVO searchRequest);

	Map<String, List<UserRoleVO>> getAllUsersWithRole();

}
