package com.oms.viewobjects.vpz;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class VPZGoalsWithAD {
	private List<VPZGoalWithAD> goalBean;

	public List<VPZGoalWithAD> getGoalBean() {
		return goalBean;
	}

	public void setGoalBean(List<VPZGoalWithAD> goalBean) {
		this.goalBean = goalBean;
	}




	
}