package com.oms.viewobjects.vpz;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.oms.viewobjects.Budget;
import com.oms.viewobjects.CPM;

@XmlRootElement(name="goalBean")
public class VPZGoal {

	private Budget budget;
	private String campaignId;
	private CPM cpm;
	private Date end;
	private String formatType;
	private BigDecimal goalValue;
	private String id;
	private String name;
	private Date start;
	private String type;
	private boolean unlimitedImpressions;
	private String variant;
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public CPM getCpm() {
		return cpm;
	}
	public void setCpm(CPM cpm) {
		this.cpm = cpm;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public BigDecimal getGoalValue() {
		return goalValue;
	}
	public void setGoalValue(BigDecimal goalValue) {
		this.goalValue = goalValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isUnlimitedImpressions() {
		return unlimitedImpressions;
	}
	public void setUnlimitedImpressions(boolean unlimitedImpressions) {
		this.unlimitedImpressions = unlimitedImpressions;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	
	
	

}
