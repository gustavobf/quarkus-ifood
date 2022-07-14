package com.github.gustavobf.ifood.mp;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.github.gustavobf.ifood.mp.dto.PedidoRealizadoDTO;
import com.github.gustavobf.ifood.mp.dto.PratoPedidoDTO;

import io.smallrye.mutiny.Uni;

@Path("carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

	private static final String CLIENTE = "cliente";

	@Inject
	io.vertx.mutiny.pgclient.PgPool client;

	@Inject
	@Channel("pedidos")
	Emitter<PedidoRealizadoDTO> emitterPedido;

	@GET
	public Uni<List<PratoCarrinho>> buscarcarrinho() {
		return PratoCarrinho.findCarrinho(client, CLIENTE);
	}

	@POST
	@Path("/prato/{idPrato}")
	public Uni<Long> adicionarPrato(@PathParam("idPrato") final Long idPrato) {
		//poderia retornar o pedido atual
		final PratoCarrinho pc = new PratoCarrinho();
		pc.cliente = CLIENTE;
		pc.prato = idPrato;
		return PratoCarrinho.save(client, CLIENTE, idPrato);

	}

	@POST
	@Path("/realizar-pedido")
	public Uni<Boolean> finalizar() {
		final PedidoRealizadoDTO pedido = new PedidoRealizadoDTO();
		final String cliente = CLIENTE;
		pedido.cliente = cliente;
		final List<PratoCarrinho> pratoCarrinho = PratoCarrinho.findCarrinho(client, cliente).await().indefinitely();
		//Utilizar mapstruts
		final List<PratoPedidoDTO> pratos = pratoCarrinho.stream().map(pc -> this.from(pc)).collect(Collectors.toList());
		pedido.pratos = pratos;

		final RestauranteDTO restaurante = new RestauranteDTO();
		restaurante.nome = "nome restaurante";
		pedido.restaurante = restaurante;
		emitterPedido.send(pedido);

		return PratoCarrinho.delete(client, cliente);
	}

	private PratoPedidoDTO from(final PratoCarrinho pratoCarrinho) {
		final PratoDTO dto = Prato.findById(client, pratoCarrinho.prato).await().indefinitely();
		return new PratoPedidoDTO(dto.nome, dto.descricao, dto.preco);
	}

}