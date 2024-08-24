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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Cozinha")
@RequestMapping("/cozinhas")
@ResponseStatus(HttpStatus.OK)
public interface CozinhaOperations {

	@GetMapping
	@Operation(summary = "Lista Cozinhas", description = "Lista todas as cozinhas")
	Page<Cozinha> listar(Pageable pageable);

	@GetMapping("/{id}")
	@Operation(summary = "Busca Cozinha", description = "Busca cozinha por {id}")
	Cozinha getPorCodigo(@Parameter(description = "ID de uma cidade", example = "1") @PathVariable Long id);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Grava nova Cozinha", description = "Grava nova cozinha")
	Cozinha adicionar(@Parameter(description = "corpo", example = "Representação de uma nova cidade") @RequestBody @Valid Cozinha cozinha);

	@PutMapping("/{id}")
	@Operation(summary = "Altera cozinha", description = "Altera cozinha por {id}")
	Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Deleta a cozinha", description = "Deleta a cozinha por {id}")
	void excluir(@PathVariable Long id);

	@GetMapping("/por-nome")
	@Operation(summary = "Busca por nome", description = "Busca cozinha por nome")
	List<Cozinha> buscaPorNome(String nome);

}