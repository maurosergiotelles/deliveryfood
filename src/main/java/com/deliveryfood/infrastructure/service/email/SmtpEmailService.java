package com.deliveryfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.deliveryfood.core.email.EmailProperties;
import com.deliveryfood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class SmtpEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private Configuration freeMarkerConfig;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mineMessage = criarMimeMessage(mensagem);

			mailSender.send(mineMessage);
		} catch (Exception e) {
			throw new EmailException(e.getMessage());
		}
	}

	public MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processarTemplate(mensagem);

		MimeMessage mineMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mineMessage, "UTF-8");

		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);
		return mineMessage;
	}

	String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException(e.getMessage());
		}
	}
}
