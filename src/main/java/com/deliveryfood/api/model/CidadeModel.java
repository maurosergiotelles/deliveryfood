package com.deliveryfood.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CidadeModel {
	private Long id;

	private String nome;

	private EstadoModel estado;
}
