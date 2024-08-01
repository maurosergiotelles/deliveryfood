package com.deliveryfood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.repository.CozinhaRepository;
import com.deliveryfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Restaurante> findAll() {
		return restauranteRepository.findAllRestaurante();
	}

	public Restaurante findById(Long id) {
		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(id);

		return optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", id)));
	}

	public Restaurante adicionar(Restaurante restaurante) {
		cozinhaExisteOuNaoEncontrada(restaurante.getCozinha().getId());

		return restauranteRepository.save(restaurante);
	}

	private void cozinhaExisteOuNaoEncontrada(Long cozinhaId) {
		if (!cozinhaRepository.existsById(cozinhaId)) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha com código %d não foi encontrado.", cozinhaId));
		}
	}

	public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
		cozinhaExisteOuNaoEncontrada(restaurante.getCozinha().getId());

		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(restauranteId);

		Restaurante restauranteEncontrado = optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", restauranteId)));

		BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formasPagamento", "endereco",
				"dataCadastro", "produto");
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

	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

}
