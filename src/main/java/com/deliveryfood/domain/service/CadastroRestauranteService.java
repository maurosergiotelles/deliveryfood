package com.deliveryfood.domain.service;

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
		Optional<Restaurante> optional = restauranteRepository.findById(id);
		if (optional.isPresent()) {
			Restaurante restaurante = optional.get();
			return restaurante;
		}

		throw new EntidadeNaoEncontradaException(String.format("Restaurante com o código %d não encontrado", id));

	}

	public Restaurante incluir(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(cozinhaId);
		if (cozinhaOptional.isPresent()) {
			Restaurante restauranteSalvo = restauranteRepository.save(restaurante);

			return restauranteSalvo;
		}

		throw new EntidadeNaoEncontradaException(String.format("Cozinha com código %d não foi encontrado", cozinhaId));
	}

	public Restaurante alterar(Long restauranteId, Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(cozinhaId);
		if (!cozinhaOptional.isPresent()) {
			throw new EntidadeChaveEstrangeiraNaoEncontradaException(
					String.format("Cozinha com o código %d não encontrada", cozinhaId));
		}

		Optional<Restaurante> restauranteOptional = restauranteRepository.findById(restauranteId);
		if (restauranteOptional.isPresent()) {
			Restaurante restauranteEncontrado = restauranteOptional.get();
			BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id");
			Restaurante restauranteSalvo = restauranteRepository.save(restauranteEncontrado);
			return restauranteSalvo;
		}
		throw new EntidadeNaoEncontradaException(
				String.format("Restaurante com o código %d não encontrado", restauranteId));

	}

}
