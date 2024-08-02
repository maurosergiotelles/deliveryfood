package com.deliveryfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
//				RestauranteModel::setTaxaFrete);

		return modelMapper;
	}
}
