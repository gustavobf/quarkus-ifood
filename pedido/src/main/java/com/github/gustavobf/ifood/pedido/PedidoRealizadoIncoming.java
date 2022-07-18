package com.github.gustavobf.ifood.pedido;

import java.io.IOException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.elasticsearch.action.index.IndexRequest;


@ApplicationScoped
public class PedidoRealizadoIncoming {

	@Inject
	ESService elastic;

	@Incoming("pedidos")
	public void lerPedidos(final PedidoRealizadoDTO dto) throws IOException {
		System.out.println("-------Recebendo Pedido----------");
		System.out.println(dto);

		final Pedido p = new Pedido();
		p.cliente = dto.cliente;
		p.pratos = new ArrayList<>();
		dto.pratos.forEach(prato -> p.pratos.add(this.from(prato)));
		final Restaurante restaurante = new Restaurante();
		restaurante.nome = dto.restaurante.nome;
		p.restaurante = restaurante;
		//		final String json = JsonbBuilder.create().toJson(dto);
		elastic.index(dto, new IndexRequest("pedidos"));
		p.persist();
	}

	private Prato from(final PratoPedidoDTO prato) {
		final Prato p = new Prato();
		p.descricao = prato.descricao;
		p.nome = prato.nome;
		p.preco = new Decimal128(prato.preco);
		return p;
	}

}
