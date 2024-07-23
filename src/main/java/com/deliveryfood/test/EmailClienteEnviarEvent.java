package com.deliveryfood.test;

import org.springframework.context.annotation.Profile;

@Profile("test")
public class EmailClienteEnviarEvent {

	public EmailClienteEnviarEvent(String mensagem) {
		System.out.println("EVENTO: " + mensagem);
	}

}
