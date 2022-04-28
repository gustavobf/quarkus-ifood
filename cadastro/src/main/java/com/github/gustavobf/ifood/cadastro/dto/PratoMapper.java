package com.github.gustavobf.ifood.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.github.gustavobf.ifood.cadastro.Prato;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface PratoMapper {
	
	PratoDTO convertToDTO(Prato p);

    Prato convertToPrato(PratoDTO dto);

    void convertToPrato(PratoDTO dto, @MappingTarget Prato prato);
	
}
