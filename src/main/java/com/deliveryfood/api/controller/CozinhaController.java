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

import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.service.CadastroCozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
/* @ResponseStatus(code = HttpStatus.CREATED) */
public class CozinhaController {

	/*
	 * return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	 */

	/*
	 * HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.LOCATION,
	 * "http://localhost:8080/cozinhas"); return
	 * ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	 */

	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<?> excluir(@PathVariable Long
	 * id) { try { cadastroCozinha.excluir(id); return
	 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
	 * e) { return ResponseEntity.notFound().build(); } catch
	 * (EntidadeEmUsoException e) { return
	 * ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); } }
	 */

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cozinha> listar() {
//		if (true) {
//			throw new IllegalArgumentException("fdsafda");
//		}
		return cadastroCozinha.findAll();
	}

	/* @ResponseStatus(code = HttpStatus.CREATED) */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cozinha getPorCodigo(@PathVariable Long id) {
		return cadastroCozinha.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinha.adicionar(cozinha);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinha.atualizar(id, cozinha);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cadastroCozinha.excluir(id);
	}

	@GetMapping("/por-nome")
	@ResponseStatus(HttpStatus.OK)
	public List<Cozinha> buscaPorNome(String nome) {
		return cadastroCozinha.findByNomeContaining(nome);
	}
}
