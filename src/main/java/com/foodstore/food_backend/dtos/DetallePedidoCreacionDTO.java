package com.foodstore.food_backend.dtos;
import lombok.Data;

@Data
public class DetallePedidoCreacionDTO {

    private Long productoId;
    private int cantidad;
}