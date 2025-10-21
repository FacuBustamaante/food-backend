package com.foodstore.food_backend.dtos;
import lombok.Data;

@Data
public class ProductoDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private String imgURL;
    private String descripcion;
    private int stock;
    private String nombreCategoria;
    private Long categoriaId;
}