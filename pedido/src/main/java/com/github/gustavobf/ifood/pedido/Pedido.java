package com.github.gustavobf.ifood.pedido;

import java.util.List;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "pedidos", database = "pedido")
public class Pedido {

	public String cliente;

	public List<Prato> pratos;

	public Restaurante Restaurante;

	public String entregador;

	public Localizacao localizacaoEntregador;

}
