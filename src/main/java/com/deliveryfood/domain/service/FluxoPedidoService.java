package com.deliveryfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.StatusPedido;
import com.deliveryfood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.CONFIRMADO));
		}

		pedido.confirmar();
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.CANCELADO));
		}

		pedido.cancelar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatusPedido().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", pedido.getCodigo(), pedido.getStatusPedido(), StatusPedido.ENTREGUE));
		}

		pedido.entregar();
	}
}
