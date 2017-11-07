package com.oms.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.tavant.api.auth.model.OMSUser;

@Entity
@Table(name = "proposal_audit")
public class ProposalAudit implements java.io.Serializable {

	private static final long serialVersionUID = -7273406063725969347L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROPOSAL_AUDIT_ID")
	private Long auditId;

	@Column(name = "PROPOSAL_VERSION_ID")
	private Long versionId;

	@Column(name = "PROPOSAL_ID")
	private Long proposalId;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private OMSUser userId;

	@ManyToOne
	@JoinColumn(name = "PROPOSAL_STATUS", referencedColumnName = "STATUS_ID")
	private ProposalStatus status;

	@NotNull
	@Column(name = "END_DATE")
	private Timestamp endDate;

	@NotNull
	@Column(name = "ACTION")
	private String action;

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public OMSUser getUserId() {
		return userId;
	}

	public void setUserId(OMSUser userId) {
		this.userId = userId;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		final int prime = 61;
		int result = 1;
		result = prime * result + ((auditId == null) ? 0 : auditId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProposalAudit other = (ProposalAudit) obj;
		if (auditId == null) {
			if (other.auditId != null)
				return false;
		} else if (!auditId.equals(other.auditId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProposalAudit [auditId=" + auditId + ", versionId=" + versionId
				+ ", proposalId=" + proposalId + ", userId=" + userId
				+ ", status=" + status + ", endDate=" + endDate + ", action="
				+ action + "]";
	}

}
