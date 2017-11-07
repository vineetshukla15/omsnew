package com.oms.viewobjects;

public class GlobalSearch {
	private String value;

	private boolean regex;

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public boolean getRegex() {
		return this.regex;
	}
}
