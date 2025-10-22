package com.food_store.backend.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleRequestDto {
    private Long productoId;
    private Integer cantidad;
}