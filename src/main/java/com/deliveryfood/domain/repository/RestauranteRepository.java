package com.deliveryfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

	@Query("from Restaurante r join fetch r.cozinha join fetch r.formasPagamento")
	List<Restaurante> findAllRestaurante();

	@Query("select r from Restaurante r join fetch r.formasPagamento where r.id = :id")
	List<FormaPagamentoModel> findAllRestauranteFormaPagamento(@Param("id") Long restauranteId);

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	List<Restaurante> buscaPorNomeECozinhaId(String nome, @Param("id") Long cozinhaId);

}
