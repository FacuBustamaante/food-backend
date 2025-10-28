package com.food_store.backend.service.impl;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.productoDtos.ProductoCreateDto;
import com.food_store.backend.entity.dto.productoDtos.ProductoDto;
import com.food_store.backend.entity.mapper.ProductoMapper;
import com.food_store.backend.repository.ICategoriaRepository;
import com.food_store.backend.repository.IProductoRepository;
import com.food_store.backend.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class ProductoService implements IProductoService {

    private final IProductoRepository iProductoRepository;
    private final ICategoriaRepository iCategoriaRepository;
    public ProductoService(IProductoRepository iProductoRepository, ICategoriaRepository iCategoriaRepository) {
        this.iProductoRepository = iProductoRepository;
        this.iCategoriaRepository = iCategoriaRepository;
    }


    @Override
    public List<ProductoDto> listarProductos() {
        return iProductoRepository.findAll().stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto buscarPorId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID no puede ser nulo"
            );
        }
        Producto productoSearch = iProductoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Categoria con ID " + id + " no encontrado"
        ));
        return ProductoMapper.toDto(productoSearch);
    }


    @Override
    public ProductoDto crearProducto(ProductoCreateDto productoCreateDto) {
        Categoria categoria = iCategoriaRepository.findById(productoCreateDto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria con id" + productoCreateDto.getCategoriaId() + " no encontrada"
                ));
        Producto productoCreate = ProductoMapper.toEntity(productoCreateDto);
        productoCreate.setCategoria(categoria);
        iProductoRepository.save(productoCreate);
        return ProductoMapper.toDto(productoCreate);
    }

    @Override
    public void eliminarProducto(Long id) {
        ProductoDto productoDelete = buscarPorId(id);
        iProductoRepository.deleteById(productoDelete.getId());
    }
}
