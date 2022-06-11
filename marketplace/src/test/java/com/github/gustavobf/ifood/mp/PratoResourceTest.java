package com.github.gustavobf.ifood.mp;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PratoResourceTest {

	@Test
	public void testBuscarPratos() {
		String resultado = given().
				when().
				get("/pratos").
				then().statusCode(Status.OK.getStatusCode()).
				extract()
				.asString();
		System.out.println(resultado);
	}
	
	

}
