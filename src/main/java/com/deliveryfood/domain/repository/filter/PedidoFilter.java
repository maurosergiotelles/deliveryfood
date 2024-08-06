package com.deliveryfood.domain.repository.filter;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {

	private Long clienteId;
	private Long restauranteId;
	private LocalDateTime dataCriacaoInicio;
	private LocalDateTime dataCriacaoFim;

}
