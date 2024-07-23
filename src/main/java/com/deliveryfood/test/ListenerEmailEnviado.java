package com.deliveryfood.test;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerEmailEnviado {

	@EventListener
	public void emailEnviado(EmailClienteEnviarEvent event) {
		System.out.println("Escutando o Evento>>>:" + event);

	}
}
