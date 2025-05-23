package com.task.exception;

public class TrackingException extends RuntimeException {
	public TrackingException(String message) {
		super(message);
	}

	public TrackingException(String message, Throwable cause) {
		super(message, cause);
	}
}
