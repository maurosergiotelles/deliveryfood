package com.deliveryfood.api.controller.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.deliveryfood.domain.model.Cozinha;

import jakarta.validation.Valid;

@RequestMapping("/cozinhas")
@ResponseStatus(HttpStatus.OK)
public interface CozinhaOperations {

	@GetMapping
	Page<Cozinha> listar(Pageable pageable);

	@GetMapping("/{id}")
	Cozinha getPorCodigo(@PathVariable Long id);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Cozinha adicionar(@RequestBody @Valid Cozinha cozinha);

	@PutMapping("/{id}")
	Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void excluir(@PathVariable Long id);

	@GetMapping("/por-nome")
	List<Cozinha> buscaPorNome(String nome);

}