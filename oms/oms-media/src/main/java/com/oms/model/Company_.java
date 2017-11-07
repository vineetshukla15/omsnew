package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.tavant.api.auth.model.Customer;

@StaticMetamodel(Company.class)
public class Company_ extends BaseEntity_{
	
	public static volatile SingularAttribute<Company, Long> companyId;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> type;
	public static volatile SingularAttribute<Company, String> address;
	public static volatile SingularAttribute<Company, Customer> customer;
	public static volatile SingularAttribute<Company, CompanyStatus> companyStatus;
}
