package com.deliveryfood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Pedido com id %d não encontrado", pedidoId)));
	}

	public List<PedidoResumoModel> findAll() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoResumoModel.class)).collect(Collectors.toList());
	}

	public PedidoModel findById(Long pedidoId) {
		Pedido pedido = buscarOuFalhar(pedidoId);
		return modelMapper.map(pedido, PedidoModel.class);
	}
}
