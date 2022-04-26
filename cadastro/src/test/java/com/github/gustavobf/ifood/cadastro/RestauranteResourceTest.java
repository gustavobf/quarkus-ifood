package com.github.gustavobf.ifood.cadastro;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.gustavobf.ifood.cadastro.util.TokenUtils;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {

	private String token;

	@BeforeEach
	public void gerarToken() throws Exception {
		token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
	}

	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void testBuscarRestaurantes() {
		String resultado = given().when().get("/restaurantes").then().statusCode(Status.OK.getStatusCode()).extract()
				.asString();
		JsonApprovals.verifyJson(resultado);
	}

	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void testAlterarRestaurante() {

		Restaurante dto = new Restaurante();
		dto.nome = "novoNome";
		Long parameterValue = 123L;

		given().with().pathParam("id", parameterValue).body(dto).when().put("/restaurantes/{id}").then()
				.statusCode(Status.NO_CONTENT.getStatusCode()).extract().asString();

		Restaurante restaurante = Restaurante.findById(parameterValue);

		Assert.assertEquals(dto.nome, restaurante.nome);

	}

	@Test
	@DataSet(value = "pratos-cenario-1.yml", executeStatementsBefore = "insert into restaurante (id, cnpj, nome, proprietario) values (0, 'cnpj', 'nome1', 'proprietario1')")
	public void testBuscarPratos() {
		String resultado = given().with().pathParam("idRestaurante", 0).when()
				.get("/restaurantes/{idRestaurante}/pratos").then().statusCode(Status.OK.getStatusCode()).extract()
				.asString();
		System.out.println(resultado);
		JsonApprovals.verifyJson(resultado);
	}

	private RequestSpecification given() {
		return RestAssured.given().contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
	}

}
