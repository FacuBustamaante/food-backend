package com.food_store.backend.entity.dto.DetallePedidoDtos;

import lombok.*;

@Data
@Builder
public class DetallePedidoDto {
    private Long id;
    private Integer cantidad;
    private double subTotal;
    private String productoNombre;
}