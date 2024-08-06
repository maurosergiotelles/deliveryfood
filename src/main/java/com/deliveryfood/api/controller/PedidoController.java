package com.deliveryfood.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.api.model.input.PedidoInput;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.service.EmissaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private ModelMapper modelMapper;

//	@GetMapping
//	public List<PedidoResumoModel> findAll() {
//		return emissaoPedido.findAll();
//	}

	@GetMapping
	public MappingJacksonValue findAllPorParametro(@RequestParam(required = false) String campos) {
		List<PedidoResumoModel> pedidoResumoModel = emissaoPedido.findAll();

		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumoModel);

		SimpleFilterProvider filterProvider = new SimpleFilterProvider();

		if (StringUtils.isBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		} else {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}

		pedidosWrapper.setFilters(filterProvider);

		return pedidosWrapper;
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel findById(@PathVariable String codigoPedido) {
		return emissaoPedido.findById(codigoPedido);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido pedido = modelMapper.map(pedidoInput, Pedido.class);
			pedido = emissaoPedido.emtir(pedido);

			return modelMapper.map(pedido, PedidoModel.class);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}

	}

}
