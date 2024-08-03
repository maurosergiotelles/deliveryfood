package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.GrupoModel;
import com.deliveryfood.api.model.input.GrupoInput;
import com.deliveryfood.domain.service.CadastroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GrupoModel findById(@PathVariable Long id) {
		return cadastroGrupo.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GrupoModel> findAll() {
		return cadastroGrupo.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		return cadastroGrupo.adicionar(grupoInput);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		return cadastroGrupo.atualizar(id, grupoInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long id) {
		cadastroGrupo.apagar(id);
	}
}
