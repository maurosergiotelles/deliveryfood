package com.deliveryfood.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {

	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private LocalDateTime dataCriacao;
	private String restauranteNome;
//	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
//	private FormaPagamentoModel formaPagamento;
//	private EnderecoModel enderecoEntrega;
//	private List<ItemPedidoModel> itens;

}
