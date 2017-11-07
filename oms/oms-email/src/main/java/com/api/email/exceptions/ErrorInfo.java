package com.api.email.exceptions;

public class ErrorInfo {
	private String path;
	private String status;
	private String message;
	private String errorCode;
	private String stackTrace;
	private long timeStamp;

	public ErrorInfo() {

	}

	public ErrorInfo(String status, String message, String errorCode,
			String stackTrace) {
		super();
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
		this.stackTrace = stackTrace;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getStackTrace() {
		return stackTrace;
	}

}