package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.repository.EstadoRepository;

@Service
public class CidadeCadastroService {

	private static final String ESTADO_COM_O_CÓDIGO_D_NÃO_ENCONTRADO = "Estado com o código %d não encontrado";

	private static final String CIDADE_NÃO_ENCONTRADA_COM_O_CÓDIGO_D = "Cidade não encontrada com o código %d";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	public Cidade findById(Long id) {
		return this.buscarOuFalhar(id);
	}

	public Cidade incluir(Cidade cidade) {
		estadoExisteOuFalha(cidade.getEstado().getId());

		return cidadeRepository.save(cidade);
	}

	public Cidade alterar(Long id, Cidade cidade) {
		estadoExisteOuFalha(cidade.getEstado().getId());

		Cidade cidadeEncontrada = this.buscarOuFalhar(id);

		BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");

		return cidadeRepository.save(cidadeEncontrada);
	}

	public void deletar(Long id) {
		if (!cidadeRepository.existsById(id)) {
			throw new EntidadeNaoEncontradaException(String.format(CIDADE_NÃO_ENCONTRADA_COM_O_CÓDIGO_D, id));
		}
		cidadeRepository.deleteById(id);
	}

	public Cidade buscarOuFalhar(Long id) {
		Optional<Cidade> optionalCidade = cidadeRepository.findById(id);

		return optionalCidade.orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(CIDADE_NÃO_ENCONTRADA_COM_O_CÓDIGO_D, id)));
	}

	private void estadoExisteOuFalha(Long estadoId) {
		if (!estadoRepository.existsById(estadoId)) {
			throw new EntidadeChaveEstrangeiraNaoEncontradaException(
					String.format(ESTADO_COM_O_CÓDIGO_D_NÃO_ENCONTRADO, estadoId));
		}
	}
}