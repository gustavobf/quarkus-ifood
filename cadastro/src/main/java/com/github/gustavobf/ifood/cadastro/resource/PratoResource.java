package com.github.gustavobf.ifood.cadastro.resource;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.gustavobf.ifood.cadastro.Prato;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {
	@GET
	public List<Prato> listar() {
		return Prato.listAll();
	}

	@POST
	@Transactional
	public Response adicionar(Prato dto) {
		dto.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void atualizar(@PathParam("id") Long id, Prato dto) {
		Optional<Prato> pratoOp = Prato.findByIdOptional(id);
		if (pratoOp.isEmpty()) {
			throw new NotFoundException();
		}

		Prato prato = pratoOp.get();
		prato.nome = dto.nome;
		prato.persist();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void deletar(@PathParam("id") Long id) {
		Optional<Prato> pratoOp = Prato.findByIdOptional(id);

		pratoOp.ifPresentOrElse(Prato::delete, () -> {
			throw new NotFoundException();
		});

	}
}
