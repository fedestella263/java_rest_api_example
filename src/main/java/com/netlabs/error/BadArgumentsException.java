package com.netlabs.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

@SuppressWarnings("serial")
public class BadArgumentsException  extends RuntimeException {
	
	private List<FieldError> fieldsErrors = new ArrayList<>();

    public BadArgumentsException(List<FieldError> fieldsErrors) {
    	this.fieldsErrors = fieldsErrors;
    }
    
    public List<FieldError> getFieldsErrors() {
    	return fieldsErrors;
    }
    
    @Override
    public String getMessage() {
    	return "Bad Arguments";
    }
}