package com.deliveryfood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEmailService {

	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processarTemplate(mensagem);
		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}
}
