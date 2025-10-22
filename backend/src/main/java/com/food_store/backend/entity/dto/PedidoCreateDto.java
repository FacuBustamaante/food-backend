package com.food_store.backend.entity.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PedidoCreateDto {
    private long idUsuario;
    private LocalDate fecha;
    private String estado;
    private List<DetalleRequestDto> detalles;
}