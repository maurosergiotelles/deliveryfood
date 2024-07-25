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

import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
/* @ResponseStatus(code = HttpStatus.CREATED) */
public class CozinhaController {

//	@Autowired
//	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {
		List<Cozinha> cozinhas = cadastroCozinha.findAll();

		return ResponseEntity.ok(cozinhas);
	}

	/* @ResponseStatus(code = HttpStatus.CREATED) */
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> getPorCodigo(@PathVariable Long id) {
		try {
			Cozinha cozinha = cadastroCozinha.findById(id);

			return ResponseEntity.ok(cozinha);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

		/*
		 * return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		 */

		/*
		 * HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.LOCATION,
		 * "http://localhost:8080/cozinhas"); return
		 * ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
		 */
	}

	@PostMapping
	public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinha);

		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> alterar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		try {
			Cozinha cozinhaAlterada = cadastroCozinha.alterar(id, cozinha);

			return ResponseEntity.ok(cozinhaAlterada);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cozinha);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			cadastroCozinha.excluir(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		}

	}
}
