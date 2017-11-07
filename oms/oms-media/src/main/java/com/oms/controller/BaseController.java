package com.oms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import com.oms.exceptions.ErrorInfo;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.service.OMSUserService;

/**
 * Base of all controllers
 */
public class BaseController {

    /**
     * The Logger for this class.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
	
	@Autowired
	private OMSUserService userService;

 /*   *//**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with Exception Attributes as JSON and HTTP
     * status code 404, not found.
     *
     * @param noResultException A NoResultException instance.
     * @param request The HttpServletRequest in which the NoResultException was
     *        raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     *         and HTTP status code 404.
     *//*
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(
            NoResultException noResultException, HttpServletRequest request) {

        logger.info("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new OMSDefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(noResultException, request,
                        HttpStatus.NOT_FOUND);

        logger.info("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.NOT_FOUND);
    }

    *//**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods. Creates a response with the
     * Exception Attributes in the response body as JSON and a HTTP status code
     * of 500, internal server error.
     *
     * @param exception An Exception instance.
     * @param request The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     *         and a HTTP status code 500.
     *//*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception exception, HttpServletRequest request) {

        logger.error("> handleException");
        logger.error("- Exception: ", exception);

        ExceptionAttributes exceptionAttributes = new OMSDefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(exception, request,
                        HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("< handleException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/

	@ExceptionHandler(OMSSystemException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(HttpServletRequest req,OMSSystemException customException) {
		//LOG.error("Exception occured in system ::" +customException.getMessage());
		StackTraceElement[] stacktrace = customException.getStackTrace();
		customException.printStackTrace();
		logger.error("Exception occured in system ::" );
		String stacktracemesaage="Issue occured in class : "+stacktrace[0].getClassName()+" , in method : "+stacktrace[0].getMethodName()+" at line number : "+stacktrace[0].getLineNumber()+" due to : "+customException.getRootCauseMessage() ;
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
		exception.printStackTrace();
		logger.error("Exception occured in system ::" +exception.getStackTrace());
		ErrorInfo errorInfo = new ErrorInfo(Status.FAILED.name(),
				"System error occured !", HttpStatus.INTERNAL_SERVER_ERROR.toString(),exception.getMessage());
		errorInfo.setPath(req.getRequestURI());
		errorInfo.setTimeStamp(new Date().getTime());
		return new ResponseEntity<ErrorInfo>(errorInfo,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorInfo> handleMultipartException(HttpServletRequest req,
			MultipartException multipartException) {
		ErrorInfo errorInfo = new ErrorInfo(Status.FAILED.name(), multipartException.getMessage(),
				HttpStatus.BAD_REQUEST.name(), null);
		errorInfo.setPath(req.getRequestURI());
		errorInfo.setTimeStamp(new Date().getTime());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(HttpMessageNotReadableException.class)
     public ResponseEntity<ErrorInfo> handleInvalidFormatError(HttpServletRequest req,
             final HttpMessageNotReadableException exception) {
         exception.printStackTrace();
         logger.error("Exception occured in system ::" +exception.getStackTrace());
         ErrorInfo errorInfo = new ErrorInfo(Status.FAILED.name(),
                 "System error occured !", HttpStatus.BAD_REQUEST.toString(),exception.getMessage());
         errorInfo.setPath(req.getRequestURI());
         errorInfo.setTimeStamp(new Date().getTime());
         return new ResponseEntity<ErrorInfo>(errorInfo,
                 HttpStatus.BAD_REQUEST);
     }
	
	protected Long findUserIDFromSecurityContext() {
		 String user =null;
	        try {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            if (null != authentication) {
	            	user =  (String)authentication.getPrincipal();
	            	
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	        }
	        return userService.findByUserName(user).getId();
	    
	    }

}
