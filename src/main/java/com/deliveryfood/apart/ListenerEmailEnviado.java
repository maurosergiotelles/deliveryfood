package com.deliveryfood.apart;

import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("apart")
@Component
public class ListenerEmailEnviado {

	@EventListener
	public void emailEnviado(EmailClienteEnviarEvent event) {
		System.out.println("Escutando o Evento>>>:" + event);

	}
}
