package com.oms.viewobjects.vpz;

import javax.xml.bind.annotation.XmlRootElement;

import com.oms.viewobjects.Budget;
import com.oms.viewobjects.CPM;

@XmlRootElement(name="campaignBean")
public class VPZCampaign {
	
	private String advertiserId;
	private String agencyId;
	private String brandId;
	private Budget budget;
	private String customId;
	private String description;
	private boolean enabled;
	private String frontLoadFactor;
	private int goalTotal;
	private String goalType;
	private String id;
	private boolean includeInForecast;
	private String name;
	private String priority;
	private String status;
	private boolean vastEnabled;
	
	private CPM cpmHigh;
	private CPM cpmLow;


	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFrontLoadFactor() {
		return frontLoadFactor;
	}

	public void setFrontLoadFactor(String frontLoadFactor) {
		this.frontLoadFactor = frontLoadFactor;
	}

	public int getGoalTotal() {
		return goalTotal;
	}

	public void setGoalTotal(int goalTotal) {
		this.goalTotal = goalTotal;
	}

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIncludeInForecast() {
		return includeInForecast;
	}

	public void setIncludeInForecast(boolean includeInForecast) {
		this.includeInForecast = includeInForecast;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isVastEnabled() {
		return vastEnabled;
	}

	public void setVastEnabled(boolean vastEnabled) {
		this.vastEnabled = vastEnabled;
	}

	public CPM getCpmHigh() {
		return cpmHigh;
	}

	public void setCpmHigh(CPM cpmHigh) {
		this.cpmHigh = cpmHigh;
	}

	public CPM getCpmLow() {
		return cpmLow;
	}

	public void setCpmLow(CPM cpmLow) {
		this.cpmLow = cpmLow;
	}
	
	

}
