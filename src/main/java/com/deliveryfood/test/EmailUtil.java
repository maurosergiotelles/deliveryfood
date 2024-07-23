package com.deliveryfood.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class EmailUtil {

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public EmailUtil() {
		System.out.println("Inicializado.");
	}

	public void enviar() {

		
		System.out.println("Enviando emailfsdafda");
		
		eventPublisher.publishEvent(new EmailClienteEnviarEvent("Prezado cliente"));
		
	}
}