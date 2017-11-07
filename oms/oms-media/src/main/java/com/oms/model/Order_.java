package com.oms.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Order.class)
public class Order_ extends BaseEntity_ {

	public static volatile SingularAttribute<Order, String> name;
	public static volatile SingularAttribute<Order, Company> advertiser;
	public static volatile SingularAttribute<Order, Date> startDate;
	public static volatile SingularAttribute<Order, Date> endDate;
	public static volatile SingularAttribute<Order, Date> dueDate;
	public static volatile SingularAttribute<Order, SalesCategory> salesCategory;
	public static volatile SingularAttribute<Order, Company> agency;
}
