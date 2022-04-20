package com.github.gustavobf.ifood.cadastro;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class PratoResourceTest {
	
	@Test
	@DataSet(value = "pratos-cenario-1.yml", executeStatementsBefore = "insert into restaurante (id, cnpj, nome, proprietario) values (0, 'cnpj', 'nome1', 'proprietario1')")
	public void testBuscarPratos() {
		String resultado = given()
				.pathParam("idRestaurante", 0)
				.when()
				.get("/restaurantes/{idRestaurante}/pratos")
				.then()
				.statusCode(Status.OK.getStatusCode())
				.extract()
				.asString();
		JsonApprovals.verifyJson(resultado);
	}

}
