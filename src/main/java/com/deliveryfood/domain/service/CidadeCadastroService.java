package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.repository.EstadoRepository;

@Service
public class CidadeCadastroService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Cidade> findAll() {
		List<Cidade> cidades = cidadeRepository.findAll();
		return cidades;
	}

	public Cidade findById(Long id) {
		Optional<Cidade> optionalCidade = cidadeRepository.findById(id);

		return optionalCidade.orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Cidade não encontrada com o código %d", id)));

	}

	public Cidade incluir(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Optional<Estado> optionalEstado = estadoRepository.findById(estadoId);

		optionalEstado.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Estado com o código %d não encontrado", estadoId)));

		return cidadeRepository.save(cidade);
	}

	public Cidade alterar(Long id, Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Optional<Estado> optionalEstado = estadoRepository.findById(estadoId);

		optionalEstado.orElseThrow(() -> new EntidadeChaveEstrangeiraNaoEncontradaException(
				String.format("Estado com código %d não existe", estadoId)));

		Optional<Cidade> optionalCidade = cidadeRepository.findById(id);

		Cidade cidadeEncontrada = optionalCidade.orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Cidade com o código %d não encontrada", id)));

		BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");

		return cidadeRepository.save(cidadeEncontrada);
	}

	public void deletar(Long id) {
		cidadeRepository.deleteById(id);
	}
}
