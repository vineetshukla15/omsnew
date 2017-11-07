package com.oms.viewobjects.vpz;

import java.util.Date;
import java.util.List;

import com.oms.viewobjects.Budget;

public class VPZADs {

	private String id;
	private String name;
	private String goalId;
	private Budget customId;
	private String description;
	private boolean enabled;
	private int weight;
	private VPZCreative creative;
	private Date start;
	private Date end;
	private List<VPZADExternalTracker> externalTrackers;

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

	public String getGoalId() {
		return goalId;
	}

	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}

	public Budget getCustomId() {
		return customId;
	}

	public void setCustomId(Budget customId) {
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public VPZCreative getCreative() {
		return creative;
	}

	public void setCreative(VPZCreative creative) {
		this.creative = creative;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<VPZADExternalTracker> getExternalTrackers() {
		return externalTrackers;
	}

	public void setExternalTrackers(List<VPZADExternalTracker> externalTrackers) {
		this.externalTrackers = externalTrackers;
	}

}
