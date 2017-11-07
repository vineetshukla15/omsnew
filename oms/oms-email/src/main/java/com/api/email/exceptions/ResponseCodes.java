package com.api.email.exceptions;


public enum ResponseCodes{
	DATA_NOT_FOUND(421),
	SYSTEM_ERROR(200),
	UNAUTHORIZED_USER(401),
	BAD_REQUEST(404),
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
