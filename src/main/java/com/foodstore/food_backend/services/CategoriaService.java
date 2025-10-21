package com.foodstore.food_backend.services;
import com.foodstore.food_backend.dtos.CategoriaDTO;
import com.foodstore.food_backend.entities.Categoria;
import com.foodstore.food_backend.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

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

    public void eliminarCategoria(Long id) {
        // Lógica de negocio: Chequear si hay productos asociados antes de eliminar
        categoriaRepository.deleteById(id);
    }
}