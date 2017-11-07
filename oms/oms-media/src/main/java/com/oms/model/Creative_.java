package com.oms.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Creative.class)
public class Creative_ extends BaseEntity_ {
	public static volatile SingularAttribute<Creative, String> name;
	public static volatile SingularAttribute<Creative, Double> width1;
	public static volatile SingularAttribute<Creative, Double> width2;
	public static volatile SingularAttribute<Creative, Double> height1;
	public static volatile SingularAttribute<Creative, Double> height2;
	public static volatile SingularAttribute<Creative, String> description;
}
