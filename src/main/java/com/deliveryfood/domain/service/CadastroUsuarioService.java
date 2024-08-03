package com.deliveryfood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.model.input.UsuarioInput;
import com.deliveryfood.api.model.input.UsuarioInputAlterarSenha;
import com.deliveryfood.api.model.input.UsuarioInputAtualizar;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.UsuarioAlterarSenhaException;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModel findById(Long id) {
		return modelMapper.map(encontraOuFalhar(id), UsuarioModel.class);
	}

	private Usuario encontraOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Usuário com código %d não encontrado", id)));
	}

	public List<UsuarioModel> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioModel.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public UsuarioModel adicionar(UsuarioInput usuarioInput) {
		Usuario usuario = modelMapper.map(usuarioInput, Usuario.class);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return modelMapper.map(usuarioSalvo, UsuarioModel.class);
	}

	@Transactional
	public UsuarioModel atualizar(Long id, UsuarioInputAtualizar usuarioInputAtualizar) {
		Usuario usuario = this.encontraOuFalhar(id);
		modelMapper.map(usuarioInputAtualizar, usuario);

		return modelMapper.map(usuario, UsuarioModel.class);
	}

	@Transactional
	public void alterarSenha(Long id, UsuarioInputAlterarSenha usuarioInputAlterarSenha) {
		Usuario usuario = this.encontraOuFalhar(id);
		if (usuario.senhaNaoCoincideCom(usuarioInputAlterarSenha.getSenhaAtual())) {
			throw new UsuarioAlterarSenhaException("Senha atual não corresponde à senha cadastrada");
		}
		usuario.setSenha(usuarioInputAlterarSenha.getNovaSenha());
	}
}
