package com.kh.rupp_dev.boukryuniversity.exception;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends NoSuchElementException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
