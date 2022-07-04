package com.github.gustavobf.ifood.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.mutiny.pgclient.PgPool;

@ApplicationScoped
public class RestauranteCadastrado {

	@Inject
	PgPool pgPool;

	@Incoming("restaurantes")
	public void receberRestaurante(final String json) {
		final Jsonb create = JsonbBuilder.create();
		final Restaurante restaurante = create.fromJson(json, Restaurante.class);

		restaurante.persist(pgPool);

	}

}
