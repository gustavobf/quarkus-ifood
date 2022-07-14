package com.github.gustavobf.ifood.mp;

import io.vertx.mutiny.sqlclient.Row;

public class RestauranteDTO {

	public Long id;

	public String nome;

	public static RestauranteDTO from(final Row row) {
		final RestauranteDTO dto = new RestauranteDTO();
		dto.id = row.getLong("id");
		dto.nome = row.getString("nome");

		return dto;

	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

}
