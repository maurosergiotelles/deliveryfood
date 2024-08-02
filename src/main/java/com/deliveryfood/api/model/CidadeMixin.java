package com.deliveryfood.api.model;

import com.deliveryfood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
