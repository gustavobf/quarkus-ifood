package com.github.gustavobf.ifood.cadastro.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
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

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.github.gustavobf.ifood.cadastro.Prato;
import com.github.gustavobf.ifood.cadastro.Restaurante;
import com.github.gustavobf.ifood.cadastro.dto.PratoDTO;
import com.github.gustavobf.ifood.cadastro.dto.PratoMapper;
import com.github.gustavobf.ifood.cadastro.dto.RestauranteDTO;
import com.github.gustavobf.ifood.cadastro.dto.RestauranteMapper;
import com.github.gustavobf.ifood.cadastro.infra.ConstraintViolationResponse;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteResource {

	@Inject
	RestauranteMapper restauranteMapper;

	@Inject
	PratoMapper pratoMapper;

	@Inject
	@Channel("restaurantes")
	Emitter<String> emitter;

	@GET
	@Counted(name = "Quantidade buscas Restaurante")
	@SimplyTimed(name = "Tempo simples de busca")
	@Timed(name = "Tempo completo de busca")
	public List<RestauranteDTO> listar() {
		final Stream<Restaurante> restaurantes = Restaurante.streamAll();
		return restaurantes.map(r -> restauranteMapper.convertToRestauranteDTO(r)).collect(Collectors.toList());

	}

	@POST
	@Transactional
	@APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
	@APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
	public Response adicionar(@Valid final RestauranteDTO dto) {
		final Restaurante restaurante = restauranteMapper.convertToRestaurante(dto);
		restaurante.persist();

		final Jsonb create = JsonbBuilder.create();
		final String json = create.toJson(restaurante);
		emitter.send(json);

		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void atualizar(@PathParam("id") final Long id, final RestauranteDTO dto) {

		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

		if (restauranteOp.isEmpty()) {
			throw new NotFoundException();
		}

		final Restaurante restaurante = restauranteOp.get();

		restauranteMapper.convertToRestaurante(dto, restaurante);

		restaurante.persist();

	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void deletar(@PathParam("id") final Long id) {
		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
		restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
			throw new NotFoundException();
		});

	}

	// Pratos

	@GET
	@Path("{idRestaurante}/pratos")
	@Tag(name = "prato")
	public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") final Long idRestaurante) {
		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}

		final Stream<Prato> pratos = Prato.stream("restaurante", restauranteOp.get());
		return pratos.map(p -> pratoMapper.convertToDTO(p)).collect(Collectors.toList());
	}

	@POST
	@Path("{idRestaurante}/pratos")
	@Transactional
	@Tag(name = "prato")
	public Response adicionarPrato(@PathParam("idRestaurante") final Long idRestaurante, final PratoDTO dto) {
		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

		if (restauranteOp.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}

		final Prato prato = pratoMapper.convertToPrato(dto);
		prato.restaurante = restauranteOp.get();
		prato.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{idRestaurante}/pratos/{id}")
	@Transactional
	@Tag(name = "prato")
	public void atualizarPrato(@PathParam("idRestaurante") final Long idRestaurante, @PathParam("id") final Long id, final PratoDTO dto) {
		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

		if (restauranteOp.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}

		final Optional<Prato> pratoOp = Prato.findByIdOptional(id);

		if (pratoOp.isEmpty()) {
			throw new NotFoundException("Prato não existe");
		}

		final Prato prato = pratoOp.get();

		pratoMapper.convertToPrato(dto, prato);

		prato.persist();
	}

	@DELETE
	@Path("{idRestaurante}/pratos/{id}")
	@Transactional
	@Tag(name = "prato")
	public void deletarPrato(@PathParam("idRestaurante") final Long idRestaurante, @PathParam("id") final Long id) {
		final Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

		if (restauranteOp.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}

		final Optional<Prato> pratoOp = Prato.findByIdOptional(id);

		pratoOp.ifPresentOrElse(Prato::delete, () -> {
			throw new NotFoundException();
		});
	}

}
