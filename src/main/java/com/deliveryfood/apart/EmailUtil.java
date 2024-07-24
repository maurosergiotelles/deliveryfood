package com.deliveryfood.apart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("apart")
@Component
public class EmailUtil {

	@Value("${host.email}")
	private String hostEmail;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public EmailUtil() {
		System.out.println("Inicializado.");
	}

	public void enviar() {

		System.out.println("Enviando emailfsdafda pelo host:>>>" + hostEmail);

		eventPublisher.publishEvent(new EmailClienteEnviarEvent("Prezado cliente"));

	}
}