package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {

		return cozinhaRepository.save(cozinha);
	}

	public Cozinha alterar(Long id, Cozinha cozinha) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);

		if (optional.isPresent()) {
			Cozinha cozinhaAtual = optional.get();

			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			Cozinha cozinhaAlterada = cozinhaRepository.save(cozinhaAtual);

			return cozinhaAlterada;
		}

		throw new EntidadeNaoEncontradaException(String.format("Cozinha com o código %d não encontrada", id));
	}

	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com o código %d não encontrada", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha com o código %d está em uso", id));
		}
	}

	public List<Cozinha> findAll() {
		return cozinhaRepository.findAll();
	}

	public Cozinha findById(Long id) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);
		if (optional.isPresent()) {
			Cozinha cozinha = optional.get();
			return cozinha;
		}
		throw new EntidadeNaoEncontradaException(String.format("Cozinha com o código %d não encontrada", id));

	}
}