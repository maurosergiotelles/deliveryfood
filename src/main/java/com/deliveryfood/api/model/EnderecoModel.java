package com.deliveryfood.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoModel {
	private String cep;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private CidadeResumoModel cidade;

}
