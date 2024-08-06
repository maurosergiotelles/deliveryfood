package com.deliveryfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.api.model.EnderecoModel;
import com.deliveryfood.api.model.input.ItemPedidoInput;
import com.deliveryfood.domain.model.Endereco;
import com.deliveryfood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		TypeMap<Endereco, EnderecoModel> typeMapEnderecoModel = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class).addMappings(mapper -> mapper.skip(ItemPedido::setId));

		typeMapEnderecoModel.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
//				RestauranteModel::setTaxaFrete);

		return modelMapper;
	}
}
