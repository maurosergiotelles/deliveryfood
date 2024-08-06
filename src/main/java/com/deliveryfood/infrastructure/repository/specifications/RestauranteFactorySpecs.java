package com.deliveryfood.infrastructure.repository.specifications;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.deliveryfood.domain.model.Restaurante;

@Component
public class RestauranteFactorySpecs {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeSemelhante(String nome) {

		return (root, query, builder) -> {

//			root.fetch("produto").fetch("");
//			root.fetch("fds");

			return builder.like(root.get("nome"), "%" + nome + "%");

		};
	}
}
