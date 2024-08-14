package com.deliveryfood.domain.service;

import java.io.IOException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryfood.api.model.FotoProdutoModel;
import com.deliveryfood.api.model.input.FotoProdutoInput;
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

		MultipartFile multipartFile = fotoProdutoInput.getArquivo();
		String nomeArquivo = fotoStorage.gerarNomeArquivo(multipartFile.getOriginalFilename());

		this.excluirFotoProduto(restauranteId, produtoId);

		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(multipartFile.getContentType());
		fotoProduto.setTamanho(multipartFile.getSize());
		fotoProduto.setNomeArquivo(nomeArquivo);

		FotoProduto fotoProdutoSaved = produtoRepository.save(fotoProduto);
		produtoRepository.flush();

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(nomeArquivo).inputStream(multipartFile.getInputStream()).length(multipartFile.getSize()).contentType(multipartFile.getContentType()).build();

		fotoStorage.armazenar(novaFoto);

		return modelMapper.map(fotoProdutoSaved, FotoProdutoModel.class);
	}

	public void excluirFotoProduto(Long restauranteId, Long produtoId) {
		Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId, produtoId);
		if (fotoProdutoOptional.isPresent()) {
//			FotoProduto foto = produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradoExceptio("Foto não encontrada."));

			FotoProduto foto = fotoProdutoOptional.get();
			produtoRepository.delete(foto);
			produtoRepository.flush();

			fotoStorage.remover(foto.getNomeArquivo());
//		} else {
//			throw new FotoProdutoNaoEncontradoExceptio("Foto não encontrada.");
		}

	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradoExceptio("Foto não encontrada."));
	}

}
