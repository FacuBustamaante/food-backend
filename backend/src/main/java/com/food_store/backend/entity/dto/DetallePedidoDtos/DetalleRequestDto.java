package com.food_store.backend.entity.dto.DetallePedidoDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleRequestDto {
    private Long productoId;
    private Integer cantidad;
}