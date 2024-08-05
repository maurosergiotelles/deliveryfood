package com.deliveryfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@GetMapping
	public List<PedidoResumoModel> findAll() {
		return emissaoPedido.findAll();
	}

	@GetMapping("/{pedidoId}")
	public PedidoModel findById(@PathVariable Long pedidoId) {
		return emissaoPedido.findById(pedidoId);

	}
}
