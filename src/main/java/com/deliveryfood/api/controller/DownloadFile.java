package com.deliveryfood.api.controller;

import java.io.FileInputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.domain.exception.ArquivoNaoEncontradoException;

@RestController
@RequestMapping("/file")
public class DownloadFile {

	@GetMapping(path = "/livro", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getFile() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "livro.pdf");
		byte[] pdf = chooseFile("livro.pdf");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).headers(headers).body(pdf);
	}

	@GetMapping(path = "/livro", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<byte[]> getTextFile() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "livro.txt");
		byte[] pdf = chooseFile("livro.txt");
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).headers(headers).body(pdf);
	}

	public byte[] chooseFile(String file) {
		try (FileInputStream inputStream = new FileInputStream(new ClassPathResource(file).getFile())) {
			return inputStream.readAllBytes();
		} catch (Exception e) {
			throw new ArquivoNaoEncontradoException("arquivo não encontrado");
		}
	}

}
