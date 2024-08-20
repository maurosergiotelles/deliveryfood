package com.deliveryfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.deliveryfood.domain.event.PedidoConfirmadoEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractAggregateRoot<Pedido> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo = gerarCodigo();

	@Column(nullable = false)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_forma_pagamento_fk"))
	@JsonIgnore
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_restaurante_fk"))
	private Restaurante restaurante;

//	@ManyToOne
//	@JoinColumn(name = "usuario_cliente_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_cliente_fk"))
//	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusPedido statusPedido = StatusPedido.CRIADO;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false, foreignKey = @ForeignKey(name = "pedido_usuario_fk"))
	private Usuario cliente;

	@Embedded
	private Endereco enderecoEntrega;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void calcularValorTotal() {
		this.getItens().forEach(ItemPedido::calcularPrecoTotal);
		this.subtotal = this.getItens().stream().map(item -> item.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	private String gerarCodigo() {
		return UUID.randomUUID().toString();
	}

	public void definirFrete() {
		this.setTaxaFrete(this.getRestaurante().getTaxaFrete());
	}

	@PrePersist
	public void atribuirPedidoAosItens() {
		this.getItens().forEach(item -> item.setPedido(this));
	}

	public void confirmar() {
		setStatusPedido(StatusPedido.CONFIRMADO);
		setDataConfirmacao(LocalDateTime.now());
		registerEvent(new PedidoConfirmadoEvent(this));
	}

	public void entregar() {
		setStatusPedido(StatusPedido.ENTREGUE);
		setDataEntrega(LocalDateTime.now());
	}

	public void cancelar() {
		setStatusPedido(StatusPedido.CANCELADO);
		setDataCancelamento(LocalDateTime.now());
	}
}
