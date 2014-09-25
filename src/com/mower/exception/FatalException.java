package com.mower.exception;

public class FatalException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message = null;

	public FatalException() {
		super();

	}

	public FatalException(String message) {
		super(message);
		this.message = message;
	}

	public FatalException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
