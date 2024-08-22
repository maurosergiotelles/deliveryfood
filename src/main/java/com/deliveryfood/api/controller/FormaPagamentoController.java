package com.deliveryfood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<FormaPagamentoModel> findById(@PathVariable Long id) {
		FormaPagamentoModel formaPagamentoModel = cadastroFormaPagamento.findById(id);
		CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
		return ResponseEntity.ok()

				.cacheControl(cacheControl)

				.body(formaPagamentoModel);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<FormaPagamentoModel>> findAll() {// ServletWebRequest request) {
//		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
//
//		String eTag = "0";
//
//		LocalDateTime dataUltimaAtualizacao = cadastroFormaPagamento.getDataUltimaAtualizacao();
//		if (dataUltimaAtualizacao != null) {
//			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond(ZoneOffset.UTC));
//		}
//
//		if (request.checkNotModified(eTag)) {
//			return null;
//		}
		List<FormaPagamentoModel> formasPagamento = cadastroFormaPagamento.findAll();

		CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);

//		return ResponseEntity.ok().eTag(eTag).cacheControl(cacheControl).body(formasPagamento);
		return ResponseEntity.ok().cacheControl(cacheControl).body(formasPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamento) {
		return cadastroFormaPagamento.adicionar(formaPagamento);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		return cadastroFormaPagamento.atualizar(id, formaPagamentoInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastroFormaPagamento.deletar(id);
	}
}
