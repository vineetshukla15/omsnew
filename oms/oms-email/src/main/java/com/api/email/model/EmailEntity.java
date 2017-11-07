package com.api.email.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "email")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailEntity {
	@Id
	@GeneratedValue
	@Column(name = "email_id")
private Long id;
private String toList;
private String ccList;
private String from;
private String mailSubject;
private Boolean mailsent;
public String getToList() {
	return toList;
}
public void setToList(String toList) {
	this.toList = toList;
}
public String getCcList() {
	return ccList;
}
public void setCcList(String ccList) {
	this.ccList = ccList;
}
public String getFrom() {
	return from;
}
public void setFrom(String from) {
	this.from = from;
}
public String getMailSubject() {
	return mailSubject;
}
public void setMailSubject(String mailSubject) {
	this.mailSubject = mailSubject;
}
public Boolean getMailsent() {
	return mailsent;
}
public void setMailsent(Boolean mailsent) {
	this.mailsent = mailsent;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public Integer getRetry() {
	return retry;
}
public void setRetry(Integer retry) {
	this.retry = retry;
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}

private String date;
private String action;
private Integer retry;

}
