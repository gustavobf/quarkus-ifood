package com.github.gustavobf.ifood.pedido;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class PedidoRealizadoIncoming {

	@Incoming("pedidos")
	public void lerPedidos(final PedidoRealizadoDTO dto) {
		System.out.println(dto);
	}

}
