package com.api.email.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.email.exceptions.ErrorInfo;
import com.api.email.exceptions.OMSSystemException;
import com.api.email.exceptions.Status;

/**
 * Base of all controllers
 */
public class BaseController {

    /**
     * The Logger for this class.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(OMSSystemException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(HttpServletRequest req,OMSSystemException customException) {
		//LOG.error("Exception occured in system ::" +customException.getMessage());
		StackTraceElement[] stacktrace = customException.getStackTrace();
		
		String stacktracemesaage="Issue occured in class : "+stacktrace[0].getClassName()+" , in method : "+stacktrace[0].getMethodName()+" at line number : "+stacktrace[0].getLineNumber();
		ErrorInfo errorInfo = new ErrorInfo(customException.getStatus(),
				customException.getMessage(), customException.getReasonCode().name(),stacktracemesaage);
		errorInfo.setPath(req.getRequestURI());
		errorInfo.setTimeStamp(new Date().getTime());
		return new ResponseEntity<ErrorInfo>(errorInfo,
				customException.getReasonCode());
	}


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req,
			final RuntimeException exception) {
		logger.error("Exception occured in system ::" +exception.getMessage());
		ErrorInfo errorInfo = new ErrorInfo(Status.FAILED.name(),
				"System error occured !", HttpStatus.INTERNAL_SERVER_ERROR.toString(),exception.getMessage());
		errorInfo.setPath(req.getRequestURI());
		errorInfo.setTimeStamp(new Date().getTime());
		return new ResponseEntity<ErrorInfo>(errorInfo,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
