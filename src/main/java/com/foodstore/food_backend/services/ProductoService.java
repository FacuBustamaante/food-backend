package com.foodstore.food_backend.services;
import com.foodstore.food_backend.dtos.ProductoDTO;
import com.foodstore.food_backend.entities.Producto;
import com.foodstore.food_backend.repositories.CategoriaRepository;
import com.foodstore.food_backend.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    private ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setImgURL(producto.getImgURL());
        dto.setStock(producto.getStock());
        // Mapeo de la relación
        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }
        return dto;
    }

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDto) {
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setImgURL(productoDto.getImgURL());
        producto.setStock(productoDto.getStock());

        // Asignar Categoría
        producto.setCategoria(categoriaRepository.findById(productoDto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + productoDto.getCategoriaId())));

        return toDTO(productoRepository.save(producto));
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoDto.getNombre());
            producto.setPrecio(productoDto.getPrecio());
            producto.setDescripcion(productoDto.getDescripcion());
            producto.setImgURL(productoDto.getImgURL());
            producto.setStock(productoDto.getStock());

            // Reasignar Categoría si el ID cambió
            if (!producto.getCategoria().getId().equals(productoDto.getCategoriaId())) {
                producto.setCategoria(categoriaRepository.findById(productoDto.getCategoriaId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + productoDto.getCategoriaId())));
            }

            return toDTO(productoRepository.save(producto));
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado para actualizar con ID: " + id));
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}