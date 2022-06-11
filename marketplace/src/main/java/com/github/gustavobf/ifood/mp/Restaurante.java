package com.github.gustavobf.ifood.mp;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public class Restaurante {
	
	public Long id;
	
	public String nome;
	
	public Localizacao localizacao;

	public static Multi<RestauranteDTO> findAll(PgPool pgPool) {
		 Uni<RowSet<Row>> preparedQuery = pgPool.query("SELECT * FROM restaurante").execute();
	        return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> {
	        	return StreamSupport.stream(rowSet.spliterator(), false);
	        })).onItem().transform(RestauranteDTO::from); 
	}
	
}
