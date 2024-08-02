package com.deliveryfood.domain.model;

import java.math.BigDecimal;

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
@Table(name = "produto")
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	private String nome;

	@Column(length = 255)
	private String descricao;

	private BigDecimal preco;

	private Boolean ativo;

	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false, foreignKey = @ForeignKey(name = "produto_restaurante_fk"))
	private Restaurante restaurante;
}
