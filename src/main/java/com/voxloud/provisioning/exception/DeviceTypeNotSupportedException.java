package com.voxloud.provisioning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeviceTypeNotSupportedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204862351204643412L;

	public DeviceTypeNotSupportedException(String message) {
		super(message);
	}
}
