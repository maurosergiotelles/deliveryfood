package com.deliveryfood.apart.infrastructure.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;

import com.deliveryfood.domain.model.Cozinha;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Profile("apart")
public abstract class CozinhaRepository {
	@PersistenceContext
	public EntityManager manager;

	public abstract List<Cozinha> todas();

	public abstract Cozinha adicionar(Cozinha cozinha);

	public abstract Cozinha findById(Long id);

	public abstract void remover(Long id);

}