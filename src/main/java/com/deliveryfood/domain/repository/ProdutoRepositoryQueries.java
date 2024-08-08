package com.deliveryfood.domain.repository;

import com.deliveryfood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto fotoProduto);

	void delete(FotoProduto fotoProduto);
}
