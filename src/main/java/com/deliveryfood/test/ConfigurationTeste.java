package com.deliveryfood.test;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ConfigurationTeste {

	@Bean
	Properties criaProperties(EmailUtil emailUtil) {
		System.out.println("emailUtil" + emailUtil);
		System.out.println("Properties iniciado");
		emailUtil.enviar();
		return new Properties();
	}

}