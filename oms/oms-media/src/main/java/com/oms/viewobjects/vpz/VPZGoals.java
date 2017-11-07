package com.oms.viewobjects.vpz;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class VPZGoals {
	private List<VPZGoal> goalBean;

	public List<VPZGoal> getGoalBean() {
		return goalBean;
	}

	public void setGoalBean(List<VPZGoal> goalBean) {
		this.goalBean = goalBean;
	}




}
