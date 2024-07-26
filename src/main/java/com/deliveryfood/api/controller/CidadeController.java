package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.service.CidadeCadastroService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeCadastroService cidadeCadastro;

	@GetMapping
	public ResponseEntity<List<Cidade>> getAll() {

		List<Cidade> cidades = cidadeCadastro.findAll();

		return ResponseEntity.ok(cidades);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {

		try {
			Cidade cidade = cidadeCadastro.findById(id);

			return ResponseEntity.ok(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Cidade cidade) {
		try {
			Cidade cidadeSalva = cidadeCadastro.incluir(cidade);

			return ResponseEntity.ok(cidadeSalva);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Cidade cidade) {
		try {
			Cidade cidadeAlterada = cidadeCadastro.alterar(id, cidade);

			return ResponseEntity.ok(cidadeAlterada);
		} catch (EntidadeChaveEstrangeiraNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		cidadeCadastro.deletar(id);

		return ResponseEntity.noContent().build();
	}

}
