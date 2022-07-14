package com.github.gustavobf.ifood.pedido;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class PedidoRealizadoIncoming {

	@Incoming("pedidos")
	public void lerPedidos(final PedidoRealizadoDTO dto) {
		System.out.println(dto);

		final Pedido pedido = new Pedido();
		pedido.cliente = dto.cliente;
		pedido.pratos = new ArrayList<Prato>();

		dto.pratos.forEach(prato -> pedido.pratos.add(this.from(prato)));
		final Restaurante restaurante = new Restaurante();
		restaurante.nome = dto.restaurante.nome;
		pedido.restaurante = restaurante;
		pedido.persist();
	}

	private Prato from(final PratoPedidoDTO prato) {
		final Prato p = new Prato();
		p.descricao = prato.descricao;
		p.nome = prato.nome;
		p.preco = new Decimal128(prato.preco);
		return p;
	}

}
