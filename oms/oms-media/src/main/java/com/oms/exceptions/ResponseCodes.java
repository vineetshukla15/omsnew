package com.oms.exceptions;


public enum ResponseCodes{
	NOT_FOUND(404),
	SYSTEM_ERROR(200),
	UNAUTHORIZED_USER(401),
	BAD_REQUEST(400),
	PRECONDITION_FAILED(412),
	INTERNAL_ERROR(500),
	CREATE_FAILED(413),
	UPDATE_FAILED(414),
	TEMPORARILY_UNAVAILABLE(503);

	private Integer responseCode;
	
	private ResponseCodes(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	
	
	

}
