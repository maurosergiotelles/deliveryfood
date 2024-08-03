package com.deliveryfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.api.model.EnderecoModel;
import com.deliveryfood.domain.model.Endereco;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		TypeMap<Endereco, EnderecoModel> typeMapEnderecoModel = modelMapper.createTypeMap(Endereco.class,
				EnderecoModel.class);

		typeMapEnderecoModel.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
//				RestauranteModel::setTaxaFrete);

		return modelMapper;
	}
}
