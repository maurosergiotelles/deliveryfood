package com.deliveryfood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.deliveryfood.core.storage.StorageProperties;
import com.deliveryfood.core.storage.StorageProperties.S3;
import com.deliveryfood.domain.exception.StorageException;

//@Service
public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {

		try {
			S3 s3 = storageProperties.getS3();

			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			ObjectMetadata objectMetadata = new ObjectMetadata();

			objectMetadata.setContentType(novaFoto.getContentType());
			objectMetadata.setContentLength(novaFoto.getLength());

			PutObjectRequest putObjectRequest = new PutObjectRequest(s3.getBucket(), caminhoArquivo, novaFoto.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage());
		}

	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorio(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("não foi possível excluir o arquivo");
		}

	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

		return FotoRecuperada.builder().url(url.toString()).build();
	}
}