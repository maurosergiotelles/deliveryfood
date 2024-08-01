package com.deliveryfood.domain.model;

import com.deliveryfood.core.validation.Groups;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@Column(name = "endereco_cep", length = 8, nullable = false)
	private String cep;

	@Column(name = "endereco_logradouro", length = 80, nullable = false)
	private String logradouro;

	@Column(name = "endereco_numero", length = 20, nullable = false)
	private String numero;

	@Column(name = "endereco_complemento", length = 30)
	private String complemento;

	@Column(name = "endereco_bairro", length = 60, nullable = false)
	private String bairro;

	@ConvertGroup(from = Default.class, to = Groups.CidadeId.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_cidade_id", nullable = false)
	private Cidade cidade;

}
