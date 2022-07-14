package com.github.gustavobf.ifood.mp;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Restaurante {

	public Long id;

	public String nome;

	public Localizacao localizacao;

	public static Multi<RestauranteDTO> findAll(final PgPool pgPool) {
		final Uni<RowSet<Row>> preparedQuery = pgPool.query("SELECT * FROM restaurante").execute();
		return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> {
			return StreamSupport.stream(rowSet.spliterator(), false);
		})).onItem().transform(RestauranteDTO::from);
	}

	public void persist(final PgPool pgPool) {
		System.out.println("Salvando");

		pgPool.preparedQuery("insert into localizacao (id, latitude, longitude) values ($1, $2, $3)").execute(
				Tuple.of(localizacao.id, localizacao.latitude, localizacao.longitude)).await().indefinitely();

		pgPool.preparedQuery("insert into restaurante (id, nome, localizacao_id) values ($1, $2, $3)").execute(
				Tuple.of(id, nome, localizacao.id)).await().indefinitely();
	}

}
