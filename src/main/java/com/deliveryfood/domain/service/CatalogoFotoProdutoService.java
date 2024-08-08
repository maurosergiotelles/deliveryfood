package com.deliveryfood.domain.service;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.api.model.FotoProdutoModel;
import com.deliveryfood.api.model.input.FotoProdutoInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.FotoProdutoNaoEncontradoExceptio;
import com.deliveryfood.domain.model.FotoProduto;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.repository.ProdutoRepository;
import com.deliveryfood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	CadastroProdutoService cadastroProduto;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FotoStorageService fotoStorage;

	@Transactional
	public FotoProdutoModel salvar(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput) throws IOException {

		FotoProduto fotoProdutoDeletar = produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Foto produto não encontrada com id restaurante %s e  id produto %s", restauranteId, produtoId)));

		MultipartFile multipartFile = fotoProdutoInput.getArquivo();
		String nomeArquivo = fotoStorage.gerarNomeArquivo(multipartFile.getOriginalFilename());

		produtoRepository.delete(fotoProdutoDeletar);
		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(multipartFile.getContentType());
		fotoProduto.setTamanho(multipartFile.getSize());
		fotoProduto.setNomeArquivo(nomeArquivo);

		FotoProduto fotoProdutoSaved = produtoRepository.save(fotoProduto);
		produtoRepository.flush();

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(nomeArquivo).inputStream(multipartFile.getInputStream()).build();
		fotoStorage.remover(fotoProdutoDeletar.getNomeArquivo());
		fotoStorage.armazenar(novaFoto);

		return modelMapper.map(fotoProdutoSaved, FotoProdutoModel.class);
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradoExceptio("Foto não encontrada."));

	}
}
