package com.oms.exceptions;

import org.springframework.http.HttpStatus;



public class OMSSystemException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String message;
	private HttpStatus reasonCode;
	private StackTraceElement[] errorTrace;
	private String rootCauseMessage;
	
	public OMSSystemException(String message) {
		super(message);
	}
	
	public OMSSystemException(String message,Throwable e) {
		super(message,e);
	}
	
	
	public OMSSystemException(String status,HttpStatus reasonCode,String message,Throwable e) {
		super(message);
		this.status = status;
		this.reasonCode=reasonCode;
		this.message = message;
		this.errorTrace=e.getStackTrace();
		this.rootCauseMessage=e.getMessage();
	}

	public OMSSystemException(String status,HttpStatus reasonCode,String message) {
		super(message);
		this.status = status;
		this.reasonCode=reasonCode;
		this.message = message;
	}

	public OMSSystemException(String status, String message,Throwable e) {
		super(message,e);
		this.status = status;
		this.message = message;
		this.errorTrace=e.getStackTrace();
	}
	

	
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(HttpStatus reason) {
		this.reasonCode = reason;
	}


	public StackTraceElement[] getErrorTrace() {
		return errorTrace;
	}

	public void setErrorTrace(StackTraceElement[] errorTrace) {
		this.errorTrace = errorTrace;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CoreSystemException [status=" + status + ", message=" + message
				+ ", reasonCode=" + reasonCode + "]";
	}

	public String getRootCauseMessage() {
		return rootCauseMessage;
	}

	public void setRootCauseMessage(String rootCauseMessage) {
		this.rootCauseMessage = rootCauseMessage;
	}
	
	
}
