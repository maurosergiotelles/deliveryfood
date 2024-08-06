package com.deliveryfood.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.CONFIRMADO));
		}

		pedido.setStatusPedido(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(LocalDateTime.now());
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.CANCELADO));
		}

		pedido.setStatusPedido(StatusPedido.CANCELADO);
		pedido.setDataCancelamento(LocalDateTime.now());
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.ENTREGUE));
		}

		pedido.setStatusPedido(StatusPedido.ENTREGUE);
		pedido.setDataEntrega(LocalDateTime.now());
	}
}
