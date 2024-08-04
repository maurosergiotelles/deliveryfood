package com.deliveryfood.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.ProdutoModel;
import com.deliveryfood.api.model.input.ProdutoInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.infrastructure.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe o produto de código %d para o restaurante de código %d", produtoId, restauranteId)));
	}

	@Transactional
	public ProdutoModel adicionar(Long restauranteId, ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		Produto produto = modelMapper.map(produtoInput, Produto.class);
		produto.setRestaurante(restaurante);

		produtoRepository.save(produto);

		return modelMapper.map(produto, ProdutoModel.class);
	}

	@Transactional
	public ProdutoModel alterar(Long restauranteId, Long produtoId, ProdutoInput produtoInput) {

		Produto produto = buscarOuFalhar(restauranteId, produtoId);

		modelMapper.map(produtoInput, produto);

		return modelMapper.map(produto, ProdutoModel.class);
	}

}
