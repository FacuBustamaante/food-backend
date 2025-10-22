package com.food_store.backend.entity.dto;

import com.food_store.backend.entity.Producto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaCreateDto {
    private String nombre;
}
