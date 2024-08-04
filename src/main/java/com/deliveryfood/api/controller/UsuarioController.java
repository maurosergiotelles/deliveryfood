package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.model.input.UsuarioInput;
import com.deliveryfood.api.model.input.UsuarioInputAlterarSenha;
import com.deliveryfood.api.model.input.UsuarioInputAtualizar;
import com.deliveryfood.domain.service.CadastroUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioModel findById(@PathVariable Long id) {
		return cadastroUsuario.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UsuarioModel> getAll() {
		return cadastroUsuario.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		return cadastroUsuario.adicionar(usuarioInput);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInputAtualizar usuarioInputAtualizar) {

		return cadastroUsuario.atualizar(id, usuarioInputAtualizar);
	}

	@PutMapping("/alterar-senha/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioInputAlterarSenha usuarioInputAlterarSenha) {
		cadastroUsuario.alterarSenha(id, usuarioInputAlterarSenha);
	}

}
