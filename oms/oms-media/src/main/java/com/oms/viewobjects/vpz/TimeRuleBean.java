package com.oms.viewobjects.vpz;

import java.util.ArrayList;
import java.util.List;

public class TimeRuleBean {
	private boolean active = true;
	private List<String> days = new ArrayList<String>();
	private int fromHour;
	private int fromMinute;
	private int toMinute;
	private int toHour;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) { 
		this.days = days;
	}

	public int getFromHour() {
		return fromHour;
	}

	public void setFromHour(int fromHour) {
		this.fromHour = fromHour;
	}

	public int getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(int fromMinute) {
		this.fromMinute = fromMinute;
	}

	public int getToMinute() {
		return toMinute;
	}

	public void setToMinute(int toMinute) {
		this.toMinute = toMinute;
	}

	public int getToHour() {
		return toHour;
	}

	public void setToHour(int toHour) {
		this.toHour = toHour;
	}

}
