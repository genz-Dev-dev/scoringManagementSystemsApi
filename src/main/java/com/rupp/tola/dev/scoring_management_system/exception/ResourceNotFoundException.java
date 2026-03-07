package com.rupp.tola.dev.scoring_management_system.exception;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends NoSuchElementException {

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
