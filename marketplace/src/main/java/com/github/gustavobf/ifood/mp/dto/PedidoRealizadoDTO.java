package com.github.gustavobf.ifood.mp.dto;

import java.util.List;

import com.github.gustavobf.ifood.mp.RestauranteDTO;

public class PedidoRealizadoDTO {
	public List<PratoPedidoDTO> pratos;

	public RestauranteDTO restaurante;

	public String cliente;
}
