package com.oms.enums;

public enum ProposalStatus {
	DRAFT("Draft"), SUBMITTED("Submitted"), APPROVED("Approved"), REJECTED("Rejected"), PENDING_APPROVAL(
			"Pending approval");

	private String statusName;

	private ProposalStatus(String statusName) {
		this.statusName = statusName;
	}

	public String statusName() {
		return statusName;
	}

}