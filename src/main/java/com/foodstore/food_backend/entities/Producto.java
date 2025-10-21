package com.foodstore.food_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String imgURL;
    private String descripcion;
    private int stock;

    // Relación m..1 con Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id") // Columna de clave foránea
    private Categoria categoria;
}
