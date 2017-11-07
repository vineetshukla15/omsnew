package com.oms.exceptions;

import org.springframework.security.core.AuthenticationException;

public final class InvalidTokenException extends AuthenticationException { 
	 
    public InvalidTokenException(String msg) { 
        super(msg);  
    } 
 
    private static final long serialVersionUID = 344699966872664622L; 
 
    
 
}
