package com.food_store.backend.service;

import com.food_store.backend.entity.dto.productoDtos.ProductoCreateDto;
import com.food_store.backend.entity.dto.productoDtos.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<ProductoDto> listarProductos();
    ProductoDto buscarPorId(Long id);
    ProductoDto crearProducto(ProductoCreateDto productoCreateDto);
    void eliminarProducto (Long id);
}
