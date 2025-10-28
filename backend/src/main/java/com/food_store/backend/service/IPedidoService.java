package com.food_store.backend.service;

import com.food_store.backend.entity.Pedido;
import com.food_store.backend.entity.dto.PedidoDtos.PedidoCreateDto;


public interface IPedidoService {
    Pedido crearPedido(PedidoCreateDto pedidoCreateDto);
}
