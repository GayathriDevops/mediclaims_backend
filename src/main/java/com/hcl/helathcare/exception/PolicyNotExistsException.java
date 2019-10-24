package com.hcl.helathcare.exception;

public class PolicyNotExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public PolicyNotExistsException(String message) {
		super(message);
	}
}
