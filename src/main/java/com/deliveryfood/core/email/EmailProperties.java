package com.deliveryfood.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@ConfigurationProperties("deliveryfood.email")
@Component
public class EmailProperties {

	public enum Implementacao {
		SMTP, FAKE, SANDBOX
	}

	@Getter
	@Setter
	public class Sandbox {
		private String destinatario;
	}

	@NotNull
	private String remetente;

	private Sandbox sandbox = new Sandbox();
	@NotNull
	private Implementacao impl = Implementacao.FAKE;
}
