package com.voxloud.provisioning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadInputException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2341789154171668156L;

	public BadInputException(String message) {
		super(message);
	}
	
}
