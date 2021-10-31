package com.voxloud.provisioning.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3437714760291204111L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
