package com.foodstore.food_backend.dtos;
import com.foodstore.food_backend.enums.Estado;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDTO {

    private Long id;
    private LocalDate fecha;
    private Estado estado;
    private Double total;

    // Información simplificada del usuario
    private Long usuarioId;
    private String nombreCompletoUsuario;

    // Lista de detalles del pedido
    private List<DetallePedidoDTO> detalles;
}