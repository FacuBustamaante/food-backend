package com.food_store.backend.service;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.ProductoCreateDto;
import com.food_store.backend.entity.mapper.ProductoMapper;
import com.food_store.backend.repository.ICategoriaRepository;
import com.food_store.backend.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    private final IProductoRepository iProductoRepository;
    private final ICategoriaRepository iCategoriaRepository;
    public ProductoService(IProductoRepository iProductoRepository, ICategoriaRepository iCategoriaRepository) {
        this.iProductoRepository = iProductoRepository;
        this.iCategoriaRepository = iCategoriaRepository;
    }


    @Override
    public List<Producto> listarProductos() {
        return iProductoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {

        return iProductoRepository.findById(id);
    }


    @Override
    public Producto crearProducto(ProductoCreateDto productoCreateDto) {

        
        Categoria categoria = iCategoriaRepository.findById(productoCreateDto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Producto productoCreate = ProductoMapper.toEntity(productoCreateDto);
        productoCreate.setCategoria(categoria);
        iProductoRepository.save(productoCreate);

        return productoCreate;
    }
}
