package com.rupp.tola.dev.scoring_management_system.exception;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends NoSuchElementException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
