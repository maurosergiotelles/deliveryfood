package com.deliveryfood.apart.infrastructure.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;

import com.deliveryfood.domain.model.Restaurante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Profile("apart")
public abstract class RestauranteRepository {
	@PersistenceContext
	public EntityManager manager;

	public abstract List<Restaurante> todas();

	public abstract Restaurante adicionar(Restaurante Restaurante);

	public abstract Restaurante findById(Long id);

	public abstract void remover(Long id);

}