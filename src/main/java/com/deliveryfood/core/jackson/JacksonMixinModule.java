package com.deliveryfood.core.jackson;

import org.springframework.stereotype.Component;

import com.deliveryfood.api.model.CidadeMixin;
import com.deliveryfood.api.model.RestauranteMixin;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}
}
