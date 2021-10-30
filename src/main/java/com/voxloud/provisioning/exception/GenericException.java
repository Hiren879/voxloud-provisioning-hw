package com.voxloud.provisioning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class GenericException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2434705635606446041L;

	public GenericException(String message) {
		super(message);
	}
}
