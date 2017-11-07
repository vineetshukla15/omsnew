package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CompanyStatus.class)
public class CompanyStatus_ extends BaseEntity_ {
	
	public static volatile SingularAttribute<CompanyStatus, Long> companyStatusId;
	public static volatile SingularAttribute<CompanyStatus, String> name;
	public static volatile SingularAttribute<CompanyStatus, Boolean> status;
}
