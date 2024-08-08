package com.deliveryfood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "foto_produto")
public class FotoProduto {

	@Id
	@Column(name = "produto_id")
	private Long id;

	@Column(name = "nome_arquivo", nullable = false)
	private String nomeArquivo;

	@Column(length = 150)
	private String descricao;

	@Column(name = "content_type", length = 80, nullable = false)
	private String contentType;

	@Column(nullable = false)
	private Long tamanho;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

}
