package com.deliveryfood.apart;

import org.springframework.context.annotation.Profile;

@Profile("apart")
public class EmailClienteEnviarEvent {

	public EmailClienteEnviarEvent(String mensagem) {
		System.out.println("EVENTO: " + mensagem);
	}

}
