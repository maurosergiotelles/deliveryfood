package com.deliveryfood.api.controller.interfaces;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.domain.model.Cidade;

import jakarta.validation.Valid;

@RequestMapping("/cidades")
public interface CidadeOperations {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<CidadeModel> getAll();

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CidadeModel findById(@PathVariable Long id);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid Cidade cidade);

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cidade atualizar(@PathVariable Long id, @Valid @RequestBody Cidade cidade);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id);

}
