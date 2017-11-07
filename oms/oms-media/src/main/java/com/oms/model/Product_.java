package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public class Product_ extends BaseEntity_ {
	
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Boolean> status;
	public static volatile SingularAttribute<Product, ProductType> productType;
	public static volatile SingularAttribute<Product, RateType> rateType;
	public static volatile SingularAttribute<Product, Creative> creatives;
	public static volatile SingularAttribute<Product, ADUnit> adUnits;
}
