package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="AUDIT_RECORD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditRecord extends BaseEntity {
	private Long auditId;
	private String userName;
	private String actionType;
	private Date dateOfAction;
	private String qualifiedMethodName;
	private String qualifiedClassName;
	private String methodArgument;

	public AuditRecord() {
	}

	public AuditRecord(String userName, String actionType, Date dateOfAction, String qualifiedMethodName,
			String qualifiedClassName, String methodArgument) {
		this.userName = userName;
		this.actionType = actionType;
		this.dateOfAction = dateOfAction;
		this.qualifiedMethodName = qualifiedMethodName;
		this.qualifiedClassName = qualifiedClassName;
		this.methodArgument = methodArgument;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "AUDIT_ID")
	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "ACTION_TYPE")
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ACTION_DATE")
	public Date getDateOfAction() {
		return dateOfAction;
	}

	public void setDateOfAction(Date dateOfAction) {
		this.dateOfAction = dateOfAction;
	}

	@Column(name = "METHOD_NAME")
	public String getQualifiedMethodName() {
		return qualifiedMethodName;
	}

	public void setQualifiedMethodName(String qualifiedMethodName) {
		this.qualifiedMethodName = qualifiedMethodName;
	}

	@Column(name = "CLASS_NAME")
	public String getQualifiedClassName() {
		return qualifiedClassName;
	}

	public void setQualifiedClassName(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}

	@Column(name = "METHOD_ARGUMENTS")
	public String getMethodArgument() {
		return methodArgument;
	}

	public void setMethodArgument(String methodArgument) {
		this.methodArgument = methodArgument;
	}
	
}
