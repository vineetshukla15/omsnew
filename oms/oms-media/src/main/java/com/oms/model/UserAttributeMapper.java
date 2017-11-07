package com.oms.model;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.tavant.api.auth.model.OMSUser;


public class UserAttributeMapper implements AttributesMapper<OMSUser>{

	@Override
	public OMSUser mapFromAttributes(Attributes paramAttributes) throws NamingException {
		OMSUser user = new OMSUser();
		 
		Attribute mail = paramAttributes.get("mail");
		Attribute firstName = paramAttributes.get("givenName");
		Attribute lastName = paramAttributes.get("sn");
		Attribute userName = paramAttributes.get("sAMAccountName");
		Attribute address1 = paramAttributes.get("streetAddress");
		Attribute city = paramAttributes.get("l");
		Attribute zip = paramAttributes.get("postalCode");
		Attribute telelphoneNo = paramAttributes.get("telephoneNumber");
		Attribute mobileNo = paramAttributes.get("mobile");
		Attribute state = paramAttributes.get("st");
		Attribute country = paramAttributes.get("c");
		
		if(userName!=null)
			user.setUsername((String)userName.get());
		if(firstName != null)
			user.setFirstName((String)firstName.get());
		if(lastName != null)
			user.setLastName((String)lastName.get());
		if(mail != null)
			user.setEmailId((String)mail.get());
		if(address1 != null)
			user.setAddress1((String)address1.get());
		
		if(city != null)
			user.setCity((String)city.get());
		if(zip != null)
			user.setZip((String)zip.get());
		if(telelphoneNo != null)
			user.setTelelphoneNo((String)telelphoneNo.get());	
		if(mobileNo != null)
			user.setMobileNo((String)mobileNo.get());
		if(state != null)
			user.setState((String)state.get());
		if(country != null)
			user.setCountry((String)country.get());
		return user;
	}
}
