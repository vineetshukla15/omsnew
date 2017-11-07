package com.oms.viewobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.model.Proposal;

/**
 * @author ritesh.nailwal
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaPlannerReview {
	private Proposal proposal;
	private long version;
	private boolean action;
	private long proposalStatus;
	private String statusDesc;
	private long userId;
	private String comment;

	public boolean getAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public long getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(long proposalStatus) {
		this.proposalStatus = proposalStatus;
	}


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusdesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}
