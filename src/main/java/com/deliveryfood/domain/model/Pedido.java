package com.deliveryfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sub_total", nullable = false)
	private BigDecimal subtotal;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;

	@CreationTimestamp
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_confirmacao")
	private LocalDateTime dataConfirmacao;

	@Column(name = "data_cancelamento")
	private LocalDateTime dataCancelamento;

	@Column(name = "data_entrega")
	private LocalDateTime dataEntrega;

	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_forma_pagamento_fk"))
	@JsonIgnore
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_restaurante_fk"))
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_cliente_fk"))
	private Cliente cliente;

	@Enumerated
	@Column(name = "status_pedido", nullable = false)
	private StatusPedido statusPedido;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_usuario_fk"))
	private Usuario usuario;

	@Embedded
	private Endereco enderecoEntrega;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido = new ArrayList<>();
}
