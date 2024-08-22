package com.deliveryfood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.deliveryfood.core.storage.StorageProperties.TipoStorage;
import com.deliveryfood.infrastructure.service.storage.FotoStorageService;
import com.deliveryfood.infrastructure.service.storage.LocalFotoStorageService;
import com.deliveryfood.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfiguration {

	@Autowired
	private StorageProperties storageProperties;

	@Bean
	AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(), storageProperties.getS3().getIdChaveAcessoSecreta());

		return AmazonS3ClientBuilder.standard().withRegion(storageProperties.getS3().getRegiao()).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}

	@Bean
	FotoStorageService fotoStorageService() {

		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		}
		return new LocalFotoStorageService();
	}

}
