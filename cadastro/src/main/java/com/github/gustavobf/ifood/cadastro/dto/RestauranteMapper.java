package com.github.gustavobf.ifood.cadastro.dto;

import org.mapstruct.Mapper;

import com.github.gustavobf.ifood.cadastro.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {
	
	Restaurante convertToRestaurante(RestauranteDTO dto);
	
	RestauranteDTO convertToRestauranteDTO(Restaurante restaurante);

}
