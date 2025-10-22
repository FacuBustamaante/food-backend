package com.food_store.backend.entity.dto;

import com.food_store.backend.entity.DetallePedido;
import com.food_store.backend.entity.enums.Estado;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PedidoDto {

    private Long idPedido;
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private List<DetallePedidoDto> detalles;
    private long idUsuario;

}
