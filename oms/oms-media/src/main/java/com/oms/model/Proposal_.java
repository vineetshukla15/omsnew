package com.oms.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.tavant.api.auth.model.OMSUser;

@StaticMetamodel(Proposal.class)
public class Proposal_ extends BaseEntity_ {

	public static volatile SingularAttribute<Proposal, String> name;
	public static volatile SingularAttribute<Proposal, Integer> percentageOfClose;
	public static volatile SingularAttribute<Proposal, Float> budget;
	public static volatile SingularAttribute<Proposal, Boolean> submitted;
	public static volatile SingularAttribute<Proposal, Date> startDate;
	public static volatile SingularAttribute<Proposal, Date> endDate;
	public static volatile SingularAttribute<Proposal, Date> dueDate;
	public static volatile SingularAttribute<Proposal, OMSUser> trafficker;
	public static volatile SingularAttribute<Proposal, OMSUser> salesPerson;
	public static volatile SingularAttribute<Proposal, SalesCategory> salesCategory;
	public static volatile SingularAttribute<Proposal, Company> agency;
	public static volatile SingularAttribute<Proposal, Company> advertiser;
	public static volatile SingularAttribute<Proposal, Terms> terms;
	public static volatile SingularAttribute<Proposal, ProposalStatus> status;
	public static volatile SingularAttribute<Proposal, Opportunity> opportunity;
	
}
