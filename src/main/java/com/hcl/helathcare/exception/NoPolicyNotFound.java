package com.hcl.helathcare.exception;

public class NoPolicyNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public  NoPolicyNotFound(String message)
	{
		super(message);
	}
}
