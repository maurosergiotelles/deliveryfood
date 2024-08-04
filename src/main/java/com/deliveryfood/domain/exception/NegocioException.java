package com.deliveryfood.domain.exception;

public class NegocioException extends RuntimeException {

	public NegocioException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
