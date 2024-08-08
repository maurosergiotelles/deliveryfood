package com.deliveryfood.apart.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.repository.ProdutoRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		return manager.merge(fotoProduto);
	}

	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
	}
}
