package com.deliveryfood.domain.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeChaveEstrangeiraNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeChaveEstrangeiraNaoEncontradaException(String message) {
		super(message);
	}

}
