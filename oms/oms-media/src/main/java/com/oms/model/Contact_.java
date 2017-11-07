package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Contact.class)
public class Contact_ extends BaseEntity_ {
	
	public static volatile SingularAttribute<Contact, String> contactName;
	public static volatile SingularAttribute<Contact, String> contactEmail;
	public static volatile SingularAttribute<Contact, String> contactMobile;
	public static volatile SingularAttribute<Contact, String> contactAddress;
	public static volatile SingularAttribute<Contact, Company> company;
}
