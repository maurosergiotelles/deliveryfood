package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Estado> estados = cadastroEstado.findAll();

		return ResponseEntity.ok(estados);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEstado(@PathVariable Long id) {
		try {
			Estado estado = cadastroEstado.findById(id);

			return ResponseEntity.ok(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<Estado> incluir(@RequestBody Estado estado) {

		Estado estadoIncluido = cadastroEstado.incluir(estado);

		return ResponseEntity.status(HttpStatus.CREATED).body(estadoIncluido);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Estado estado) {

		try {
			Estado estadoAlterado = cadastroEstado.alterar(id, estado);

			return ResponseEntity.ok(estadoAlterado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {

		try {
			cadastroEstado.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}

	}
}
