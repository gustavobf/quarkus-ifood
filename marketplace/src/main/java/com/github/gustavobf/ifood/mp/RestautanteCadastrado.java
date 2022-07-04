package com.github.gustavobf.ifood.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class RestautanteCadastrado {

	@Incoming("restaurantes")
	public void receberRestaurante(final String json) {
		final Jsonb create = JsonbBuilder.create();
		final Restaurante restaurante = create.fromJson(json, Restaurante.class);
		System.out.println(json);
		System.out.println(restaurante);
	}

}
