package com.deliveryfood.infrastructure.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.deliveryfood.core.storage.StorageProperties;
import com.deliveryfood.domain.exception.StorageException;
import com.deliveryfood.domain.service.FotoStorageService;

//@Service
public class LocalFotoStorageService { // implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;

//	@Override
	public void armazenar(FotoStorageService.NovaFoto novaFoto) {
		try {
			novaFoto.getInputStream();
			Path path = getArquivoPath(novaFoto.getNomeArquivo());

			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}

	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}

//	@Override
	public void remover(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

//	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível recuperar o arquivo.", e);
		}
	}

//		MultipartFile arquivo = fotoProdutoInput.getArquivo();
//
//		String originalFilename = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
//
//		Path path = Path.of("C:\\Users\\casa\\Downloads\\", originalFilename);
//
//		try {
//			arquivo.transferTo(path);
//		} catch (Exception e) {
//			throw new NegocioException(e.getMessage());
//		}

}
