package com.deliveryfood.apart.infrastructure.repository.impl;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.apart.infrastructure.repository.CozinhaRepository;
import com.deliveryfood.domain.model.Cozinha;

import jakarta.persistence.TypedQuery;

@Profile("apart")
@Component
public class CozinhaRepositoryImpl extends CozinhaRepository {

	@Override
	public List<Cozinha> todas() {
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);

		return query.getResultList();
	}

	@Transactional
	@Override
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	@Override
	public Cozinha findById(Long id) {
		return manager.find(Cozinha.class, id);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Cozinha cozinha = findById(id);

		manager.remove(cozinha);
	}
}