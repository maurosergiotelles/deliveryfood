package com.deliveryfood.apart.infrastructure.repository.impl;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.apart.infrastructure.repository.RestauranteRepository;
import com.deliveryfood.domain.model.Restaurante;

import jakarta.persistence.TypedQuery;

@Profile("apart")
@Component
public class RestauranteRepositoryImpl extends RestauranteRepository {

	@Override
	public List<Restaurante> todas() {
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);

		return query.getResultList();
	}

	@Transactional
	@Override
	public Restaurante adicionar(Restaurante Restaurante) {
		return manager.merge(Restaurante);
	}

	@Override
	public Restaurante findById(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Restaurante Restaurante = findById(id);

		manager.remove(Restaurante);
	}
}