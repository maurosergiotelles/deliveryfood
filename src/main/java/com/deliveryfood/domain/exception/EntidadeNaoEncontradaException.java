package com.deliveryfood.domain.exception;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException {

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
