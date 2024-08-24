package com.deliveryfood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.controller.interfaces.CidadeOperations;
import com.deliveryfood.api.model.CidadeModel;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.service.CidadeCadastroService;
//		ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
//		cidadeModel.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(cidadeModel.getId()).withSelfRel());

@RestController
public class CidadeController implements CidadeOperations {

	@Autowired
	private CidadeCadastroService cidadeCadastro;

	public CollectionModel<CidadeModel> getAll() {
		return cidadeCadastro.findAll();
	}

	public CidadeModel findById(Long id) {
		return cidadeCadastro.findById(id);
	}

	public CidadeModel adicionar(Cidade cidade) {
		return cidadeCadastro.adicionar(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidade) {
		return cidadeCadastro.atualizar(id, cidade);
	}

	public void deletar(Long id) {
		cidadeCadastro.deletar(id);
	}

}