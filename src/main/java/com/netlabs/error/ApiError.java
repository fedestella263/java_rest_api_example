package com.netlabs.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", locale = "es-UY", timezone = "America/Montevideo")
	private LocalDateTime timestamp;
	private String message;
	private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
	    this.status = status;
	}

	public ApiError(HttpStatus status, String message) {
		this(); 
		this.status = status;	
		this.message = message;
	}

	public ApiError(HttpStatus status, String message, List<FieldError> errors) {
		this();
		this.status = status;	
		this.message = message;
		this.setFieldErrors(errors);
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}
	
	public void setFieldErrors(List<FieldError> errors) {
        for (FieldError e : errors) {
    		fieldErrors.add(new FieldErrorDTO(e.getField(), e.getDefaultMessage()));
        }
	}
}