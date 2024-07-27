package com.deliveryfood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.repository.CozinhaRepository;
import com.deliveryfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Restaurante> getAll() {
		return restauranteRepository.findAll();
	}

	public Restaurante findById(Long id) {
		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(id);

		return optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", id)));
	}

	public Restaurante incluir(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> optionalCozinha = cozinhaRepository.findById(cozinhaId);

		optionalCozinha.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Cozinha com código %d não foi encontrado", cozinhaId)));

		return restauranteRepository.save(restaurante);

	}

	public Restaurante alterar(Long restauranteId, Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Optional<Cozinha> optionalCozinha = cozinhaRepository.findById(cozinhaId);

		optionalCozinha.orElseThrow(() -> new EntidadeChaveEstrangeiraNaoEncontradaException(
				String.format("Cozinha com o código %d não encontrada", cozinhaId)));

		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(restauranteId);

		Restaurante restauranteEncontrado = optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", restauranteId)));

		BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id");
		return restauranteRepository.save(restauranteEncontrado);
	}

	public List<Restaurante> findByBetweenTaxaInicialETaxaFinal(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> buscaPorNomeECozinhaId(String nome, Long cozinhaId) {
		return restauranteRepository.buscaPorNomeECozinhaId(nome, cozinhaId);
	}

	public List<Restaurante> porNomeETaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

}
