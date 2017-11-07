package com.oms.settings.errors;

import java.util.Collection;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.exceptions.Status;

public class GenericResponse<E> {
	private Status status;
    private String message;
    private String error;
    private Collection<E> data;
    
    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    public GenericResponse(Status status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

    public GenericResponse(Status status, String message, Collection<E> data) {
		this(status, message);
		this.data = data;
	}
    
    public GenericResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public GenericResponse(final List<FieldError> fieldErrors, final List<ObjectError> globalErrors) {
        super();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.message = mapper.writeValueAsString(fieldErrors);
            this.error = mapper.writeValueAsString(globalErrors);
        } catch (final JsonProcessingException e) {
            this.message = "";
            this.error = "";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Collection<E> getData() {
		return data;
	}

	public void setData(Collection<E> data) {
		this.data = data;
	}
	
}
