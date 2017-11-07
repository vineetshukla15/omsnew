package com.oms.viewobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsVO {
	private String username;
	private String mobileNo;
	private String firstName;
	private String lastName;
	private String emailId;
	@JsonIgnore
	private String tokenStatus;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getTokenStatus() {
		return tokenStatus;
	}
	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
	
	
}
