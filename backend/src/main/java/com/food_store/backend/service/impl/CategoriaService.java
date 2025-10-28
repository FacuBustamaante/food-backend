package com.food_store.backend.service.impl;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaCreateDto;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaDto;
import com.food_store.backend.entity.mapper.CategoriaMapper;
import com.food_store.backend.repository.ICategoriaRepository;
import com.food_store.backend.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {

    private final ICategoriaRepository iCategoriaRepository;

    @Autowired
    public CategoriaService(ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
    }

    @Override
    public List<CategoriaDto> listarCategoria() {
        return iCategoriaRepository.findAll().stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDto crearCategoria(CategoriaCreateDto categoriaCreateDto) {

        if (categoriaCreateDto.getNombre().isEmpty() || categoriaCreateDto.getNombre().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre de la categoría no puede ser nulo o estar vacío"
            );
        }
        String stringSearch = categoriaCreateDto.getNombre().trim().toLowerCase();
        Optional<Categoria> checkCategoria = iCategoriaRepository.findByNombre(stringSearch);

        if (checkCategoria.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con el nombre: " + categoriaCreateDto.getNombre());
        }

        categoriaCreateDto.setNombre(stringSearch);
        Categoria categoriaCreate = CategoriaMapper.toEntity(categoriaCreateDto);
        iCategoriaRepository.save(categoriaCreate);

        return CategoriaMapper.toDto(categoriaCreate);
    }

    @Override
    public CategoriaDto buscarCategoria(String categoriaNombre) {
        if (categoriaNombre == null || categoriaNombre.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre de la categoría no puede ser nulo o estar vacío"
            );
        }
        String categoriaSearch = categoriaNombre.trim().toLowerCase();
        Categoria categoria = iCategoriaRepository.findByNombre(categoriaSearch)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La categoria " + categoriaNombre + " no fue encontrada"
                ));
        return CategoriaMapper.toDto(categoria);
    }
    @Override
    public CategoriaDto buscarCategoriaPorId(Long id){
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID no puede ser nulo"
            );
        }
        Categoria categoriaSearch = iCategoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria con ID " + id + " no encontrado"
                ));
        return CategoriaMapper.toDto(categoriaSearch);
    }
    @Override
    public void eliminarCategoria(String categoria) {
        CategoriaDto categoriaSearch = buscarCategoria(categoria);
        iCategoriaRepository.deleteByNombre(categoriaSearch.getNombre());
    }




}
