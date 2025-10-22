package com.food_store.backend.controller;

import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.CategoriaDto;
import com.food_store.backend.entity.dto.ProductoCreateDto;
import com.food_store.backend.entity.dto.ProductoDto;
import com.food_store.backend.entity.mapper.CategoriaMapper;
import com.food_store.backend.entity.mapper.ProductoMapper;
import com.food_store.backend.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final IProductoService iProductoService;

    public ProductoController(IProductoService iProductoService) {
        this.iProductoService = iProductoService;
    }

    @GetMapping("/listarProductos")
    public List<ProductoDto> listarProductos(){

        return iProductoService.listarProductos().stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ProductoDto> buscarPorId(@PathVariable Long id) {
        return iProductoService.buscarPorId(id)
                .map(ProductoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody ProductoCreateDto productoCreateDto) {
        Producto response = iProductoService.crearProducto(productoCreateDto);
        ProductoDto responseDto = ProductoMapper.toDto(response);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

}
