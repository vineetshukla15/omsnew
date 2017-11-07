package com.oms.viewobjects;

import java.util.List;

public class DashboardStatusVO {
	List<String> status;
	Long userId;

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
