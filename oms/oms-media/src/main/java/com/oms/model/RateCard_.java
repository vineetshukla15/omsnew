package com.oms.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RateCard.class)
public class RateCard_ extends BaseEntity_ {
	public static volatile SingularAttribute<RateCard, BigDecimal> basePrice;
	public static volatile SingularAttribute<RateCard, String> sectionsName;
	public static volatile SingularAttribute<RateCard, String> notes;
	public static volatile SingularAttribute<RateCard, Boolean> isActive;
	public static volatile SingularAttribute<RateCard, Boolean> isRatecardrounded;

	public static volatile SingularAttribute<RateCard, Product> product;
	public static volatile SingularAttribute<RateCard, List<ADUnit>> adUnits;
	public static volatile SingularAttribute<RateCard, List<Premium>> premiums;
//	public static volatile SingularAttribute<RateCard, Product> seasonalDiscounts;

}
