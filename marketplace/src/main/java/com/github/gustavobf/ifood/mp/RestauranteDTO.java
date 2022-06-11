package com.github.gustavobf.ifood.mp;

import io.vertx.mutiny.sqlclient.Row;

public class RestauranteDTO {

	public Long id;

	public String nome;

	public static RestauranteDTO from(Row row) {
		RestauranteDTO dto = new RestauranteDTO();
		dto.id = row.getLong("id");
		dto.nome = row.getString("nome");

		return dto;

	}

}
