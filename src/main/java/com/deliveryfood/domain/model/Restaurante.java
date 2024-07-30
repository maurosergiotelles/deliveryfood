package com.deliveryfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "restaurante")
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String nome;

	@JsonIgnore
	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete = BigDecimal.ZERO;

	/*
	 * @JsonIgnoreProperties("hibernateLazyInitializer") para
	 * quando @ManyToOne(fetch = FetchType.LAZY) e não tiver @JsonIgnore
	 */
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false, foreignKey = @ForeignKey(name = "restaurante_cozinha_fk"))

	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Cozinha cozinha;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id", foreignKey = @ForeignKey(name = "restaurante_restaurante_forma_pagamentofk")), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id", foreignKey = @ForeignKey(name = "forma_pagamento_id_fk")))
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@Embedded
	@JsonIgnore
	private Endereco endereco;

	@OneToMany(mappedBy = "restaurante")
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();

}
