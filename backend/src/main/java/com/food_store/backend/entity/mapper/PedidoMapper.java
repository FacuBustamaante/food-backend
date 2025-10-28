package com.food_store.backend.entity.mapper;

import com.food_store.backend.entity.Pedido;
import com.food_store.backend.entity.dto.DetallePedidoDtos.DetallePedidoDto;
import com.food_store.backend.entity.dto.PedidoDtos.PedidoCreateDto;
import com.food_store.backend.entity.dto.PedidoDtos.PedidoDto;

import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoDto toDto(Pedido pedido){
        if (pedido == null) return null;

        return PedidoDto.builder()
                .idPedido(pedido.getId_pedido())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado())
                .total(pedido.getTotal())
                .detalles(
                        pedido.getDetalles() == null ? null :
                                pedido.getDetalles().stream()
                                        .map(det -> DetallePedidoDto.builder()
                                                .id(det.getId())
                                                .cantidad(det.getCantidad())
                                                .subTotal(det.getSubTotal())
                                                .productoNombre(det.getProducto().getNombre())
                                                .build()
                                        ).collect(Collectors.toList())
                )
                .idUsuario(pedido.getUsuario().getId())
                .build();
    }

    public static Pedido toEntity(PedidoCreateDto pedidoCreateDto) {

        if (pedidoCreateDto == null) return null;

        Pedido pedido = Pedido.builder()
                .fecha(pedidoCreateDto.getFecha())
                .build();
        return  pedido;
    }

}
