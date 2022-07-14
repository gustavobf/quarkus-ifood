package com.github.gustavobf.ifood.pedido;

import java.math.BigDecimal;

public class PratoPedidoDTO {

	public String nome;

	public String descricao;

	public BigDecimal preco;

	public PratoPedidoDTO() {
		super();
	}

	public PratoPedidoDTO(final String nome, final String descricao, final BigDecimal preco) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

}
