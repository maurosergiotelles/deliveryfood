package com.deliveryfood.infrastructure.repository.specifications;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.repository.filter.PedidoFilter;

import jakarta.persistence.criteria.Predicate;

@Component
public class PedidoFactorySpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {

		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));

		};
	}
}
