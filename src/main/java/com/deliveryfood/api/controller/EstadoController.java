package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.model.Estado;
import com.deliveryfood.infrastructure.repository.EstadoRepository;

@RestController
@RequestMapping(value = "/estados", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	public List<Estado> getAll() {
		return estadoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Estado getEstado(@PathVariable("id") Long id) {
		return estadoRepository.findById(id).get();
	}

}
