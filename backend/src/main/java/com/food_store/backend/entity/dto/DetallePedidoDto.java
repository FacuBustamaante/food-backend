package com.food_store.backend.entity.dto;

import lombok.*;

@Data
@Builder
public class DetallePedidoDto {
    private Long id;
    private Integer cantidad;
    private double subTotal;
    private String productoNombre;
}