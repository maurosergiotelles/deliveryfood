package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.service.CidadeCadastroService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeCadastroService cidadeCadastro;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cidade> getAll() {
		return cidadeCadastro.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cidade findById(@PathVariable Long id) {
		return cidadeCadastro.findById(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade incluir(@RequestBody Cidade cidade) {
		try {
			return cidadeCadastro.incluir(cidade);
		} catch (Exception e) {
			throw new EntidadeChaveEstrangeiraNaoEncontradaException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cidade alterar(@PathVariable Long id, @RequestBody Cidade cidade) {
		return cidadeCadastro.alterar(id, cidade);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cidadeCadastro.deletar(id);
	}

}