package com.github.gustavobf.ifood.pedido;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.handler.sockjs.SockJSHandler;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

	@Inject
	Vertx vertx;

	@Inject
	EventBus eventBus;

	void startup(@Observes final Router router) {
		router.route("/localizacoes*").handler(this.localizacaoHandler());
	}

	private SockJSHandler localizacaoHandler() {
		final SockJSHandler handler = SockJSHandler.create(vertx);
		final PermittedOptions permitted = new PermittedOptions();
		permitted.setAddress("novaLocalizacao");
		new SockJSBridgeOptions().addOutboundPermitted(permitted);
		//BridgeOptions bridgeOptions = new BridgeOptions().addOutboundPermitted(permitted);
		final SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permitted);
		handler.bridge(bridgeOptions);
		return handler;
	}

	@GET
	public List<PanacheMongoEntityBase> hello() {
		return Pedido.listAll();
	}

	@POST
	@Path("{idPedido}/localizacao")
	public Pedido novaLocalizacao(@PathParam("idPedido") final String idPedido, final Localizacao localizacao) {
		final Pedido pedido = Pedido.findById(new ObjectId(idPedido));

		pedido.localizacaoEntregador = localizacao;
		final String json = JsonbBuilder.create().toJson(localizacao);
		eventBus.requestAndForget("novaLocalizacao", json);
		pedido.persistOrUpdate();
		return pedido;
	}

}