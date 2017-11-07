package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ADUnit.class)
public class ADUnit_ extends BaseEntity_ {
	
	public static volatile SingularAttribute<ADUnit, String> name;
	public static volatile SingularAttribute<ADUnit, String> displayName;	
	public static volatile SingularAttribute<ADUnit, Boolean> isActive;
}
