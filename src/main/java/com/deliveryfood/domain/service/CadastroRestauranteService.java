package com.deliveryfood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.repository.CozinhaRepository;
import com.deliveryfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<RestauranteModel> findAll() {
		List<Restaurante> restaurantes = restauranteRepository.findAllRestaurante();

		return restaurantes.stream().map(restaurante -> modelMapper.map(restaurante, RestauranteModel.class))
				.collect(Collectors.toList());
	}

	public RestauranteModel findById(Long id) {

		Restaurante restaurante = buscarOuFalhar(id);

		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	@Transactional
	public RestauranteModel adicionar(RestauranteInput restauranteInput) {
		cozinhaExisteOuNaoEncontrada(restauranteInput.getCozinha().getId());

		Restaurante restaurante = modelMapper.map(restauranteInput, Restaurante.class);

		Restaurante restauranteSaved = restauranteRepository.save(restaurante);

		return modelMapper.map(restauranteSaved, RestauranteModel.class);
	}

	private void cozinhaExisteOuNaoEncontrada(Long cozinhaId) {
		if (!cozinhaRepository.existsById(cozinhaId)) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha com código %d não foi encontrado.", cozinhaId));
		}
	}

	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.inativar();
	}

	@Transactional
	public RestauranteModel atualizar(Long restauranteId, RestauranteInput restauranteInput) {
		cozinhaExisteOuNaoEncontrada(restauranteInput.getCozinha().getId());

		Restaurante restauranteEncontrado = buscarOuFalhar(restauranteId);
		modelMapper.map(restauranteInput, restauranteEncontrado);

		Restaurante restauranteSalvo = restauranteRepository.save(restauranteEncontrado);
		return modelMapper.map(restauranteSalvo, RestauranteModel.class);
	}

	private Restaurante buscarOuFalhar(Long restauranteId) {
		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(restauranteId);

		return optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", restauranteId)));
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
