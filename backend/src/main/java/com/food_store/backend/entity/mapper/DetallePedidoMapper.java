package com.food_store.backend.entity.mapper;

import com.food_store.backend.entity.DetallePedido;
import com.food_store.backend.entity.dto.DetallePedidoDtos.DetalleRequestDto;

public class DetallePedidoMapper {

    public static DetallePedido toEntity(DetalleRequestDto detalleRequestDto){

        if (detalleRequestDto == null) return null;

        return DetallePedido.builder()
                .cantidad(detalleRequestDto.getCantidad())
                .build();
    }
}
