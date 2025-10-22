package com.food_store.backend.service;

import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.ProductoCreateDto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarProductos();
    Optional<Producto> buscarPorId(Long id);
    Producto crearProducto(ProductoCreateDto productoCreateDto);
}
