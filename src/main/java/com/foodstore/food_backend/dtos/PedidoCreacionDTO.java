package com.foodstore.food_backend.dtos;
import lombok.Data;
import java.util.List;

@Data
public class PedidoCreacionDTO {

    private Long usuarioId; // Para identificar al usuario que realiza el pedido
    private List<DetallePedidoCreacionDTO> detalles;
}