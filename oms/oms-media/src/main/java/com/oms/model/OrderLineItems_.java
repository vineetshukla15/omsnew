package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OrderLineItems.class)
public class OrderLineItems_ extends BaseEntity_ {
	public static volatile SingularAttribute<OrderLineItems, String> type;
	public static volatile SingularAttribute<OrderLineItems, Product> product;
	public static volatile SingularAttribute<OrderLineItems, AudienceTargetType> audienceTargetType;
	public static volatile SingularAttribute<OrderLineItems, SpecType> specType;
	public static volatile SingularAttribute<OrderLineItems, Proposal> proposal;
}
