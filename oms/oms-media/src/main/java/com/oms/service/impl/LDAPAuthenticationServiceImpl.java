package com.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.OMSUser;

import com.oms.model.UserAttributeMapper;
import com.oms.service.AuthenticationService;

@SuppressWarnings("deprecation")
@Service("ldapAuthenticationService")
public class LDAPAuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	LdapTemplate ldapTemplate;
	

/*	@Override
	public UserToken authenticate(String userName, String password) throws IOException, URISyntaxException {
		Filter filter = new EqualsFilter("sAMAccountName", userName);
		if(ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password)){
			String token = tokenGeneratorService.generateToken(userName);
			UserToken userToken = new UserToken();
			userToken.setUserToken(token);
			return userToken;
		}
		return null;
	}*/

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List searchUserByLastName(String lastName) {
		  AndFilter filter = new AndFilter();
	      filter.and(new EqualsFilter("sn", lastName));
	      List ldapUsers = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributeMapper());
	      return ldapUsers;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OMSUser> searchUserByFirstName(String firstName) {
		  AndFilter filter = new AndFilter();
	      filter.and(new LikeFilter("givenName", firstName+"*"));
	      List ldapUsers = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributeMapper());
	      return ldapUsers;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OMSUser> searchUserByLoginName(String loginName) {
		  AndFilter filter = new AndFilter();
	      filter.and(new EqualsFilter("sAMAccountName", loginName));
	      List ldapUsers = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributeMapper());
	      return ldapUsers;
	}
}
