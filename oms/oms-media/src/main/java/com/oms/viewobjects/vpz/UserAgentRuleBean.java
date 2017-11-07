package com.oms.viewobjects.vpz;

import java.util.ArrayList;
import java.util.List;

public class UserAgentRuleBean {
	private List<String> browsers = new ArrayList<String>();
	private List<String> operatingSystems = new ArrayList<String>();
	private String access= "ALLOW";

	public List<String> getBrowsers() {
		return browsers;
	}

	public void setBrowsers(List<String> browsers) {
		this.browsers = browsers;
	}

	public List<String> getOperatingSystems() {
		return operatingSystems;
	}

	public void setOperatingSystems(List<String> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}