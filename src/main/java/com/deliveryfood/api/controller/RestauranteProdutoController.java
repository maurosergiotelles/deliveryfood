package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.ProdutoModel;
import com.deliveryfood.api.model.input.ProdutoInput;
import com.deliveryfood.domain.service.CadastroProdutoService;
import com.deliveryfood.domain.service.CadastroRestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoModel> listarProdutos(@PathVariable Long restauranteId) {
		return cadastroRestaurante.findProdutosBy(restauranteId);
	}

	@GetMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel getProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return cadastroRestaurante.findByIdByProduto(restauranteId, produtoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		return cadastroProduto.adicionar(restauranteId, produtoInput);
	}

	@PutMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		return cadastroProduto.alterar(restauranteId, produtoId, produtoInput);
	}
}