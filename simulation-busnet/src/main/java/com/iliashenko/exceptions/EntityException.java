package com.iliashenko.exceptions;

public class EntityException extends Exception {

	private static final long serialVersionUID = 2822096060137625001L;

	public EntityException(String msg) {
		super(msg);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}
}
