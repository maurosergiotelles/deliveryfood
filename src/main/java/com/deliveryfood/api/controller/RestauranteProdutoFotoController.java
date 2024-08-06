package com.deliveryfood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.api.model.input.FotoProdutoInput;
import com.deliveryfood.domain.exception.NegocioException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		String originalFilename = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();

		Path path = Path.of("C:\\Users\\casa\\Downloads\\", originalFilename);

		try {
			arquivo.transferTo(path);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}

	}
}
