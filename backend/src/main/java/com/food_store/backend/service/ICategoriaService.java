package com.food_store.backend.service;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaCreateDto;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaDto;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    List<CategoriaDto> listarCategoria();
    CategoriaDto crearCategoria(CategoriaCreateDto categoriaCreateDto);
    CategoriaDto buscarCategoria(String categoriaNombre);
    CategoriaDto buscarCategoriaPorId(Long id);
    void eliminarCategoria(String categoria);
}
