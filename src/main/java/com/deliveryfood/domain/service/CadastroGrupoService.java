package com.deliveryfood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GrupoRepository grupoRepository;

	public GrupoModel findById(Long id) {
		Grupo grupo = encontrarOuFalhar(id);

		return modelMapper.map(grupo, GrupoModel.class);
	}

	public List<GrupoModel> findAll() {
		List<Grupo> grupos = grupoRepository.findAll();

		return grupos.stream().map(grupo -> modelMapper.map(grupo, GrupoModel.class)).collect(Collectors.toList());
	}

	private Grupo encontrarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Grupo não encontrado com o id %d", id)));
	}

	@Transactional
	public GrupoModel adicionar(GrupoInput grupoInput) {
		Grupo grupo = modelMapper.map(grupoInput, Grupo.class);
		Grupo grupoSaved = grupoRepository.save(grupo);

		return modelMapper.map(grupoSaved, GrupoModel.class);
	}

	@Transactional
	public GrupoModel atualizar(Long id, GrupoInput grupoInput) {
		Grupo grupoAtual = this.encontrarOuFalhar(id);
		modelMapper.map(grupoInput, grupoAtual);

		return modelMapper.map(grupoAtual, GrupoModel.class);
	}

	@Transactional
	public void apagar(Long id) {
		try {
			grupoRepository.deleteById(this.encontrarOuFalhar(id).getId());
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Código %d está em uso", id));
		}
	}

}
