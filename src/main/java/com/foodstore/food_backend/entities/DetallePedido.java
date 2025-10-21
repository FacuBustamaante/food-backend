package com.foodstore.food_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private Double subtotal;

    // Relación m..1 con Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Relación m..1 con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}