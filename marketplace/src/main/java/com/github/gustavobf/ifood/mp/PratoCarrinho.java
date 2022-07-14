package com.github.gustavobf.ifood.mp;

import java.util.ArrayList;
import java.util.List;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class PratoCarrinho {

	public String cliente;

	public Long prato;

	public static Uni<Long> save(final PgPool client, final String cliente, final Long prato) {
		return client.preparedQuery("INSERT INTO prato_cliente (cliente, prato) VALUES ($1, $2) RETURNING (prato)").execute(
				Tuple.of(cliente, prato))

				.map(pgRowSet -> pgRowSet.iterator().next().getLong("prato"));
	}

	public static Uni<List<PratoCarrinho>> findCarrinho(final PgPool client, final String cliente) {
		return client.preparedQuery("select * from prato_cliente where cliente = $1 ").execute(Tuple.of(cliente))
				.map(pgRowSet -> {
					final List<PratoCarrinho> list = new ArrayList<>(pgRowSet.size());
					for (final Row row : pgRowSet) {
						list.add(toPratoCarrinho(row));
					}
					return list;
				});
	}

	private static PratoCarrinho toPratoCarrinho(final Row row) {
		final PratoCarrinho pc = new PratoCarrinho();
		pc.cliente = row.getString("cliente");
		pc.prato = row.getLong("prato");
		return pc;
	}

	public static Uni<Boolean> delete(final PgPool client, final String cliente) {
		return client.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
				.map(pgRowSet -> pgRowSet.rowCount() == 1);

	}

}
