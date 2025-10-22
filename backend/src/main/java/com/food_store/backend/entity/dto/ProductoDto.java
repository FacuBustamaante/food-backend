package com.food_store.backend.entity.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imgURL;
    private int stock;
    private String categoriaNombre;

}
