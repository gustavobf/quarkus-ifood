package com.github.gustavobf.ifood.pedido;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ESService{

	@Inject
	RestHighLevelClient restHighLevelClient;

	public void index(final PedidoRealizadoDTO dto, final IndexRequest index) throws IOException {
		index.source(JsonObject.mapFrom(dto).toString(), XContentType.JSON);
		restHighLevelClient.index(index, RequestOptions.DEFAULT);
	}


}