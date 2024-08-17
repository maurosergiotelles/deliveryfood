package com.deliveryfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.deliveryfood.core.email.EmailProperties;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEmailService {

	@Autowired
	private EmailProperties emailProperties;

	@Override
	public MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {

		MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

		helper.setTo(emailProperties.getSandbox().getDestinatario());

		return mimeMessage;

	}

}
