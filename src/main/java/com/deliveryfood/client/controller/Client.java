package com.deliveryfood.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Client {

	@GetMapping("/client/**")
	public void getClients() {

	}
}
