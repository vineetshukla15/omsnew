package com.oms.viewobjects.vpz;

import java.util.ArrayList;
import java.util.List;

public class VPZGoalRule {

	private String parentId;

	private List<FrequencyRuleBean> frequencyRules = new ArrayList<FrequencyRuleBean>();
	private List<LocationRuleBean> locationRules = new ArrayList<LocationRuleBean>();
	private List<TimeRuleBean> timeRules = new ArrayList<TimeRuleBean>();
	private List<TagAndPartnerRuleBean> tagAndPartnerRules = new ArrayList<TagAndPartnerRuleBean>();
	private List<CategoryRuleBean> categoryRules = new ArrayList<CategoryRuleBean>();
	private List<IpRuleBean> ipRules = new ArrayList<IpRuleBean>();
	private List<UserAgentRuleBean> userAgentRules = new ArrayList<UserAgentRuleBean>();

	private boolean ignoreParentLocationRules = true;
	private boolean ignoreParentTagRules = true;
	private boolean ignoreParentContentRules = true;
	private boolean ignoreParentTimeRules = true;
	private boolean ignoreParentFrequencyRules = true;
	private boolean ignoreParentIpRules = true;
	private boolean ignoreParentUserAgentRules = true;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<FrequencyRuleBean> getFrequencyRules() {
		return frequencyRules;
	}

	public void setFrequencyRules(List<FrequencyRuleBean> frequencyRules) {
		this.frequencyRules = frequencyRules;
	}

	public List<LocationRuleBean> getLocationRules() {
		return locationRules;
	}

	public void setLocationRules(List<LocationRuleBean> locationRules) {
		this.locationRules = locationRules;
	}

	public List<TimeRuleBean> getTimeRules() {
		return timeRules;
	}

	public void setTimeRules(List<TimeRuleBean> timeRules) {
		this.timeRules = timeRules;
	}

	public List<TagAndPartnerRuleBean> getTagAndPartnerRules() {
		return tagAndPartnerRules;
	}

	public void setTagAndPartnerRules(List<TagAndPartnerRuleBean> tagAndPartnerRules) {
		this.tagAndPartnerRules = tagAndPartnerRules;
	}

	public List<CategoryRuleBean> getCategoryRules() {
		return categoryRules;
	}

	public void setCategoryRules(List<CategoryRuleBean> categoryRules) {
		this.categoryRules = categoryRules;
	}

	public List<IpRuleBean> getIpRules() {
		return ipRules;
	}

	public void setIpRules(List<IpRuleBean> ipRules) {
		this.ipRules = ipRules;
	}

	public List<UserAgentRuleBean> getUserAgentRules() {
		return userAgentRules;
	}

	public void setUserAgentRules(List<UserAgentRuleBean> userAgentRules) {
		this.userAgentRules = userAgentRules;
	}

	public boolean isIgnoreParentLocationRules() {
		return ignoreParentLocationRules;
	}

	public void setIgnoreParentLocationRules(boolean ignoreParentLocationRules) {
		this.ignoreParentLocationRules = ignoreParentLocationRules;
	}

	public boolean isIgnoreParentTagRules() {
		return ignoreParentTagRules;
	}

	public void setIgnoreParentTagRules(boolean ignoreParentTagRules) {
		this.ignoreParentTagRules = ignoreParentTagRules;
	}

	public boolean isIgnoreParentContentRules() {
		return ignoreParentContentRules;
	}

	public void setIgnoreParentContentRules(boolean ignoreParentContentRules) {
		this.ignoreParentContentRules = ignoreParentContentRules;
	}

	public boolean isIgnoreParentTimeRules() {
		return ignoreParentTimeRules;
	}

	public void setIgnoreParentTimeRules(boolean ignoreParentTimeRules) {
		this.ignoreParentTimeRules = ignoreParentTimeRules;
	}

	public boolean isIgnoreParentFrequencyRules() {
		return ignoreParentFrequencyRules;
	}

	public void setIgnoreParentFrequencyRules(boolean ignoreParentFrequencyRules) {
		this.ignoreParentFrequencyRules = ignoreParentFrequencyRules;
	}

	public boolean isIgnoreParentIpRules() {
		return ignoreParentIpRules;
	}

	public void setIgnoreParentIpRules(boolean ignoreParentIpRules) {
		this.ignoreParentIpRules = ignoreParentIpRules;
	}

	public boolean isIgnoreParentUserAgentRules() {
		return ignoreParentUserAgentRules;
	}

	public void setIgnoreParentUserAgentRules(boolean ignoreParentUserAgentRules) {
		this.ignoreParentUserAgentRules = ignoreParentUserAgentRules;
	}

}
