package com.oms.viewobjects;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class DashBoardProposalVO {
	private Long proposalId;
	private String proposalName;
	private String status;
	private Timestamp dueDate;
	private String planner;
	private String advertiser;
	private String salesPerson;
	private String proposalTrafficker;
	private BigDecimal proposalCost;
	private String salesCategory;
	private Date updatedDate;
	public Long getProposalId() {
		return proposalId;
	}
	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getAdvertiser() {
		return advertiser;
	}
	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}
	public String getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	public String getProposalTrafficker() {
		return proposalTrafficker;
	}
	public void setProposalTrafficker(String proposalTrafficker) {
		this.proposalTrafficker = proposalTrafficker;
	}
	public BigDecimal getProposalCost() {
		return proposalCost;
	}
	public void setProposalCost(BigDecimal proposalCost) {
		this.proposalCost = proposalCost;
	}
	public String getSalesCategory() {
		return salesCategory;
	}
	public void setSalesCategory(String salesCategory) {
		this.salesCategory = salesCategory;
	}
	public String getProposalName() {
		return proposalName;
	}
	public void setProposalName(String proposalName) {
		this.proposalName = proposalName;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
