package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	private static final String COZINHA_COM_O_CÓDIGO_D_ESTÁ_EM_USO = "Cozinha com o código %d está em uso";
	private static final String COZINHA_COM_O_CÓDIGO_D_NÃO_ENCONTRADA = "Cozinha com o código %d não encontrada";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public Cozinha alterar(Long id, Cozinha cozinha) {
		Cozinha cozinhaAtual = this.buscarOutFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return cozinhaRepository.save(cozinhaAtual);
	}

	public void excluir(Long id) {
		if (!cozinhaRepository.existsById(id)) {
			throw new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_CÓDIGO_D_NÃO_ENCONTRADA, id));
		}

		try {
			cozinhaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_COM_O_CÓDIGO_D_ESTÁ_EM_USO, id));
		}
	}

	public List<Cozinha> findAll() {
		return cozinhaRepository.findAll();
	}

	public Cozinha findById(Long id) {
		return this.buscarOutFalhar(id);
	}

	public List<Cozinha> findByNomeContaining(String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}

	public Cozinha buscarOutFalhar(Long id) {
		Optional<Cozinha> optionalCozinha = cozinhaRepository.findById(id);

		return optionalCozinha.orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(COZINHA_COM_O_CÓDIGO_D_NÃO_ENCONTRADA, id)));

	}
}