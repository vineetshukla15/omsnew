package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LineItems.class)
public class LineItems_ extends BaseEntity_ {
	public static volatile SingularAttribute<LineItems, String> type;
	public static volatile SingularAttribute<LineItems, Product> product;
	public static volatile SingularAttribute<LineItems, AudienceTargetType> audienceTargetType;
	public static volatile SingularAttribute<LineItems, SpecType> specType;
	public static volatile SingularAttribute<LineItems, Proposal> proposal;
}
