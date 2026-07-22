package com.gym_membership.exceptions;

public class AiResponseParsingException extends RuntimeException {

	 public AiResponseParsingException(String message) {
	        super(message);
	    }

	    public AiResponseParsingException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
