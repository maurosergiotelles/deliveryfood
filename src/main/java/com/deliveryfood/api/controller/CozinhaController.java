package com.deliveryfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.infrastructure.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {
		List<Cozinha> cozinhas = cozinhaRepository.findAll();

		return ResponseEntity.ok(cozinhas);
	}

//	@ResponseStatus(code = HttpStatus.CREATED)
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> getPorCodigo(@PathVariable Long id) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);

		if (optional.isPresent()) {
			Cozinha cozinha = optional.get();
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();

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
		Cozinha cozinhaSalva = cozinhaRepository.save(cozinha);

		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> alterar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

		Optional<Cozinha> optional = cozinhaRepository.findById(id);

		if (optional.isPresent()) {
			Cozinha cozinhaAtual = optional.get();
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			Cozinha cozinhaAlterada = cozinhaRepository.save(cozinhaAtual);

			return ResponseEntity.ok(cozinhaAlterada);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cozinha);

	}
}
