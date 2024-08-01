package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> findAll() {
		return estadoRepository.findAll();
	}

	public Estado findById(Long id) {
		Optional<Estado> optionalEstado = estadoRepository.findById(id);
		return optionalEstado.orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Estado com o código %d não encontrado", id)));
	}

	public Estado adicionar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado atualizar(Long id, Estado estado) {
		Optional<Estado> optionalEstado = estadoRepository.findById(id);
		Estado estadoLocalizado = optionalEstado.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Estado com o código %d não foi encontrado", id)));

		BeanUtils.copyProperties(estado, estadoLocalizado, "id");

		return estadoRepository.save(estadoLocalizado);

	}

	public void deletar(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado com o código %d está em uso", id));
		}

	}
}
