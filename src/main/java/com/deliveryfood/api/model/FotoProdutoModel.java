package com.deliveryfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private long tamanho;

}
