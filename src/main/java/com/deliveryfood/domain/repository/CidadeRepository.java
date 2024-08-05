package com.deliveryfood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deliveryfood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	@Query("select d from Cidade d join fetch d.estado")
	List<Cidade> findAllCidades();

}
