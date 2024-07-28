package com.deliveryfood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public ResponseEntity<List<Restaurante>> getAll() {
		List<Restaurante> restaurantes = cadastroRestaurante.findAllC();

		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPorId(@PathVariable Long id) {
		try {
			Restaurante restaurante = cadastroRestaurante.findById(id);

			return ResponseEntity.ok(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteSalvo = cadastroRestaurante.incluir(restaurante);

			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

		try {
			Restaurante restauranteAlterado = cadastroRestaurante.alterar(id, restaurante);

			return ResponseEntity.ok(restauranteAlterado);
		} catch (EntidadeChaveEstrangeiraNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@GetMapping("/por-taxa-frete")
	public ResponseEntity<List<Restaurante>> porTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return ResponseEntity.ok(cadastroRestaurante.findByBetweenTaxaInicialETaxaFinal(taxaInicial, taxaFinal));

	}

	@GetMapping("/por-nome-cozinhaId")
	public ResponseEntity<List<Restaurante>> porNomeECozinhaId(String nome, Long cozinhaId) {
		return ResponseEntity.ok(cadastroRestaurante.buscaPorNomeECozinhaId(nome, cozinhaId));

	}

	@GetMapping("/por-nome-taxaFrete")
	public ResponseEntity<List<Restaurante>> porNomeETaxaFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return ResponseEntity.ok(cadastroRestaurante.porNomeETaxaFrete(nome, taxaFreteInicial, taxaFreteFinal));

	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> comFreteGratis(String nome) {
		return cadastroRestaurante.findComFreteGratis(nome);
	}

}
