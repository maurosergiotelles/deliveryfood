package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.controller.interfaces.CidadeOperations;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.service.CidadeCadastroService;

@RestController
public class CidadeController implements CidadeOperations {

	@Autowired
	private CidadeCadastroService cidadeCadastro;

	public List<Cidade> getAll() {
		return cidadeCadastro.findAll();
	}

	public Cidade findById(Long id) {
		return cidadeCadastro.findById(id);
	}

	public Cidade adicionar(Cidade cidade) {
		return cidadeCadastro.adicionar(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidade) {
		return cidadeCadastro.atualizar(id, cidade);
	}

	public void deletar(Long id) {
		cidadeCadastro.deletar(id);
	}

}