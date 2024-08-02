package com.deliveryfood.api.model.input;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	@NotBlank
	private String nome;

	@PositiveOrZero
	@NotNull
	private BigDecimal taxaFrete = BigDecimal.ZERO;

	@NotNull
	@Valid
	private CozinhaInput cozinha;

}
