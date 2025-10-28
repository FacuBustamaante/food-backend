package com.food_store.backend.entity.dto.categoriaDtos;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {
    private Long id;
    private String nombre;
    private List<String> productos;
}

