package com.deliveryfood.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long quantidade;

	@Column(name = "preco_unitario", nullable = false)
	private BigDecimal precoUnitario;

	private BigDecimal precoTotal;

	@Column(length = 255)
	private String observacao;

	public void calcularPrecoTotal() {
		BigDecimal precoUnitario = this.getPrecoUnitario();
		Long quantidade = this.getQuantidade();

		if (precoUnitario == null) {
			precoUnitario = BigDecimal.ZERO;
		}

		if (quantidade == null) {
			quantidade = 0L;
		}

		this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "pedido_item_fk"), nullable = false)
	private Pedido pedido;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "item_pedido_produto_fk"), nullable = false)
	private Produto produto;
}
