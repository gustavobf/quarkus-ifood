package com.github.gustavobf.ifood.pedido;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "pedidos", database = "pedido")
public class Pedido extends PanacheMongoEntity {

	public String cliente;

	public List<Prato> pratos;

	public Restaurante restaurante;

	public String entregador;

	public Localizacao localizacaoEntregador;

}
