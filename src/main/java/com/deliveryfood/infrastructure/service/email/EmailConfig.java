package com.deliveryfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.core.email.EmailProperties;
import com.deliveryfood.domain.service.EnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
		case FAKE:
			return new FakeEnvioEmailService();
		case SMTP:
			return new SmtpEmailService();
		case SANDBOX:
			return new SandboxEnvioEmailService();
		default:
			throw new IllegalArgumentException("Unexpected value: " + emailProperties.getImpl());
		}

	}
}
