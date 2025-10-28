package com.food_store.backend.entity.mapper;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaCreateDto;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaDto;

import java.util.stream.Collectors;

public class CategoriaMapper {

    public static CategoriaDto toDto(Categoria categoria) {
        if (categoria == null) return null;

        return CategoriaDto.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .productos(
                        categoria.getProductosLista() == null
                                ? null
                                : categoria.getProductosLista()
                                .stream()
                                .map(Producto::getNombre)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static Categoria toEntity(CategoriaCreateDto dto) {
        if (dto == null) return null;

        return Categoria.builder()
                .nombre(dto.getNombre())
                .build();
    }
}