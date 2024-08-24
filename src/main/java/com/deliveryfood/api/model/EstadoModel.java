package com.deliveryfood.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstadoModel extends RepresentationModel<EstadoModel> {
	public Long id;

	private String nome;

}
