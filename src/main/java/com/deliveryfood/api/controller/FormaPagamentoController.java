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

import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.model.input.FormaPagamentoInput;
import com.deliveryfood.domain.service.CadastroFormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoModel findById(@PathVariable Long id) {
		return cadastroFormaPagamento.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<FormaPagamentoModel> findAll() {
		return cadastroFormaPagamento.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamento) {
		return cadastroFormaPagamento.adicionar(formaPagamento);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoModel atualizar(@PathVariable Long id,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		return cadastroFormaPagamento.atualizar(id, formaPagamentoInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastroFormaPagamento.deletar(id);
	}
}
