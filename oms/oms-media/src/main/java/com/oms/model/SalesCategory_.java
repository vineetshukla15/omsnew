package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SalesCategory.class)
public class SalesCategory_ extends BaseEntity_{

	public static volatile SingularAttribute<SalesCategory, Long> salesCatagoryId;
	public static volatile SingularAttribute<SalesCategory, String> name;
	public static volatile SingularAttribute<SalesCategory, Boolean> status;
}





