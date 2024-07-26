package com.deliveryfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliveryfood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
