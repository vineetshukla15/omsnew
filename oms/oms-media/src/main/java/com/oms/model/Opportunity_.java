package com.oms.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Opportunity.class)
public class Opportunity_ extends BaseEntity_ {

	public static volatile SingularAttribute<Opportunity, String> name;
	public static volatile SingularAttribute<Opportunity, Integer> percentageOfClose;
	public static volatile SingularAttribute<Opportunity, Float> budget;
	public static volatile SingularAttribute<Opportunity, Boolean> submitted;
	public static volatile SingularAttribute<Opportunity, Date> startDate;
	public static volatile SingularAttribute<Opportunity, Date> endDate;
	public static volatile SingularAttribute<Opportunity, Date> dueDate;
	public static volatile SingularAttribute<Opportunity, SalesCategory> salesCategory;
	public static volatile SingularAttribute<Opportunity, Company> agency;
	public static volatile SingularAttribute<Opportunity, Company> advertiser;
}
