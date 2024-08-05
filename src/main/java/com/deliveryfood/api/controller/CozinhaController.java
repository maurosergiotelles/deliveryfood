package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.controller.interfaces.CozinhaOperations;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.service.CadastroCozinhaService;

@RestController
public class CozinhaController implements CozinhaOperations {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public List<Cozinha> listar() {
		return cadastroCozinha.findAll();
	}

	public Cozinha getPorCodigo(Long id) {
		return cadastroCozinha.findById(id);
	}

	public Cozinha adicionar(Cozinha cozinha) {
		return cadastroCozinha.adicionar(cozinha);
	}

	public Cozinha atualizar(Long id, Cozinha cozinha) {
		return cadastroCozinha.atualizar(id, cozinha);
	}

	public void excluir(Long id) {
		cadastroCozinha.excluir(id);
	}

	public List<Cozinha> buscaPorNome(String nome) {
		return cadastroCozinha.findByNomeContaining(nome);
	}

	/*
	 * return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	 */

	/*
	 * HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.LOCATION,
	 * "http://localhost:8080/cozinhas"); return
	 * ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	 */

	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<?> excluir( Long id) { try {
	 * cadastroCozinha.excluir(id); return ResponseEntity.noContent().build(); }
	 * catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.notFound().build(); } catch (EntidadeEmUsoException e) {
	 * return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); } }
	 */

}
