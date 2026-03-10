package com.rupp.tola.dev.scoring_management_system.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")

public class ResourceNotFoundException extends ApiException {

	public ResourceNotFoundException(String resourceName, String message) {
		super(HttpStatus.NOT_FOUND, String.format("%s not found", resourceName, message));
	}

	public ResourceNotFoundException(String resourceName, Boolean status) {
		super(HttpStatus.NOT_FOUND, String.format("%s not found", resourceName, status));
	}

	public ResourceNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
