package com.github.gustavobf.ifood.pedido;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

	public PedidoDeserializer() {
		super(PedidoRealizadoDTO.class);
	}

}
