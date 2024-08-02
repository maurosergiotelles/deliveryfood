package com.deliveryfood.api.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.model.Endereco;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RestauranteMixin {

	/*
	 * @JsonIgnoreProperties("hibernateLazyInitializer") para
	 * quando @ManyToOne(fetch = FetchType.LAZY) e não tiver @JsonIgnore
	 */
//	@JsonIgnoreProperties("hibernateLazyInitializer")

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	private OffsetDateTime dataCadastro;

	@JsonIgnore
	private OffsetDateTime dataAtualizacao;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();

}
