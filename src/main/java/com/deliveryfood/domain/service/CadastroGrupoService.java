package com.deliveryfood.domain.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.PermissaoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Grupo;
import com.deliveryfood.domain.model.Permissao;
import com.deliveryfood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	public GrupoModel findById(Long id) {
		Grupo grupo = buscarOuFalhar(id);

		return modelMapper.map(grupo, GrupoModel.class);
	}

	public List<GrupoModel> findAll() {
		List<Grupo> grupos = grupoRepository.findAll();

		return grupos.stream().map(grupo -> modelMapper.map(grupo, GrupoModel.class)).collect(Collectors.toList());
	}

	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Grupo não encontrado com o id %d", id)));
	}

	@Transactional
	public GrupoModel adicionar(GrupoInput grupoInput) {
		Grupo grupo = modelMapper.map(grupoInput, Grupo.class);
		Grupo grupoSaved = grupoRepository.save(grupo);

		return modelMapper.map(grupoSaved, GrupoModel.class);
	}

	@Transactional
	public GrupoModel atualizar(Long id, GrupoInput grupoInput) {
		Grupo grupoAtual = this.buscarOuFalhar(id);
		modelMapper.map(grupoInput, grupoAtual);

		return modelMapper.map(grupoAtual, GrupoModel.class);
	}

	@Transactional
	public void apagar(Long id) {
		try {
			grupoRepository.deleteById(this.buscarOuFalhar(id).getId());
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Código %d está em uso", id));
		}
	}

	public Set<PermissaoModel> findByIdGrupo(Long grupoId) {
		Grupo grupo = this.buscarOuFalhar(grupoId);
		Set<Permissao> permissoes = grupo.getPermissoes();
		return permissoes.stream().map(permissao -> modelMapper.map(permissao, PermissaoModel.class)).collect(Collectors.toSet());
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = this.buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}

	@Transactional
	public void desassociar(Long grupoId, Long permissaoId) {
		Grupo grupo = this.buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

		grupo.removerPermissao(permissao);

	}

}
