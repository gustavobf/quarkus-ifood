package com.github.gustavobf.ifood.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.github.gustavobf.ifood.cadastro.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {
	
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "localizacao.id", ignore = true)
	Restaurante convertToRestaurante(RestauranteDTO dto);

	Restaurante convertToRestaurante(RestauranteDTO dto, @MappingTarget Restaurante restaurante);


	RestauranteDTO convertToRestauranteDTO(Restaurante restaurante);

}
