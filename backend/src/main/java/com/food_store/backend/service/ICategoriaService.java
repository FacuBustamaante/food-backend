package com.food_store.backend.service;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.dto.CategoriaCreateDto;

import java.util.List;

public interface ICategoriaService {

    List<Categoria> listarCategoria();
    Categoria crearCategoria(CategoriaCreateDto categoriaCreateDto);
}
