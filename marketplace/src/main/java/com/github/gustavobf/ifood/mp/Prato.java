package com.github.gustavobf.ifood.mp;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public class Prato {

	public Long id;

	public String nome;

	public String descricao;

	public BigDecimal preco;

	 public static Multi<PratoDTO> findAll(PgPool pgPool) {
	        Uni<RowSet<Row>> preparedQuery = pgPool.query("SELECT * FROM prato").execute();
	        return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> {
	        	return StreamSupport.stream(rowSet.spliterator(), false);
	        })).onItem().transform(PratoDTO::from);  
	    }
	
	 
	
	

}
