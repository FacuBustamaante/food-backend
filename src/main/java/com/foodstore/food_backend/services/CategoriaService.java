package com.foodstore.food_backend.services;
import com.foodstore.food_backend.dtos.CategoriaDTO;
import com.foodstore.food_backend.entities.Categoria;
import com.foodstore.food_backend.repositories.CategoriaRepository;
import com.foodstore.food_backend.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    private PedidoRepository pedidoRepository;
    // --- Mappers (Implementación simple in-situ) ---

    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

    private Categoria toEntity(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }

    // --- Métodos CRUD ---

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDto) {
        // Lógica de negocio: Asegurar que el nombre de la categoría sea único
        Categoria categoria = toEntity(categoriaDto);
        return toDTO(categoriaRepository.save(categoria));
    }

    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDto) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(categoriaDto.getNombre());
            return toDTO(categoriaRepository.save(categoria));
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada para actualizar con ID: " + id));
    }

    // Eliminar una categoría por id
    public void eliminarCategoria(Long id) {
        // Verificar que la categoría exista
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría no encontrada con id: " + id));

        // Chequear si hay productos/pedidos asociados a la categoría
        boolean tienePedidos = pedidoRepository.existsByCategoriaId(id);
        if (tienePedidos) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se puede eliminar la categoría porque tiene pedidos asociados.");
        }

        // Si pasa las validaciones, eliminar
        categoriaRepository.deleteById(id);
    }
}