package com.food_store.backend.entity.mapper;

import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.ProductoCreateDto;
import com.food_store.backend.entity.dto.ProductoDto;

public class ProductoMapper {


    public static ProductoDto toDto(Producto producto) {
        if (producto == null) return null;

        return ProductoDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .imgURL(producto.getImgURL())
                .stock(producto.getStock())
                .categoriaNombre(producto.getCategoria().getNombre())
                .build();

    }

    public static Producto toEntity(ProductoCreateDto productoCreateDto){
        if(productoCreateDto == null) return null;

        return Producto.builder()
                .nombre(productoCreateDto.getNombre())
                .descripcion(productoCreateDto.getDescripcion())
                .precio(productoCreateDto.getPrecio())
                .imgURL(productoCreateDto.getImgURL())
                .stock(productoCreateDto.getStock())
                .build();
    }
}
