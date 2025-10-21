package com.foodstore.food_backend.dtos;
import lombok.Data;

@Data
public class DetallePedidoDTO {

    private Long id;
    private int cantidad;
    private Double subtotal;

    // Información simplificada del producto
    private Long productoId;
    private String nombreProducto;
    private Double precioUnitario;
}