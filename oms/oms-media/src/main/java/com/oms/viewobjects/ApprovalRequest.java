package com.oms.viewobjects;

public class ApprovalRequest {
	private String approve;
	private boolean action;
	private long proposalStatus;
	private long userid;
	private String comment;
	public String isApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}
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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	
}
