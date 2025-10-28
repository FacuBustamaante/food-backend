package com.food_store.backend.entity.dto.productoDtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoCreateDto {
    private String nombre;
    private String descripcion;
    private double precio;
    private String imgURL;
    private int stock;
    private Long categoriaId;
}
