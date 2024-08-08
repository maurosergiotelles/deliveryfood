package com.deliveryfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

	@Query("from Produto p where p.restaurante.id = :restaurante and p.id = :produto")
	Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

	@Query("From Produto p where p.ativo = true and p.restaurante.id = :restauranteId")
	List<Produto> findAtivosByRestaurante(Long restauranteId);

	@Query("select f from FotoProduto f join f.produto p where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
