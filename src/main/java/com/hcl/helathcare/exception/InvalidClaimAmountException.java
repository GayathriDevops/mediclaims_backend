package com.hcl.helathcare.exception;

public class InvalidClaimAmountException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidClaimAmountException(String message) {
		super(message);
	}
}
