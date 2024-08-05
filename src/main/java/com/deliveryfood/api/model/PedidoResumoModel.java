package com.deliveryfood.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoModel {

	private Long id;
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
