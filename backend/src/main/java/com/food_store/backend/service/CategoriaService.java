package com.food_store.backend.service;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.dto.CategoriaCreateDto;
import com.food_store.backend.entity.mapper.CategoriaMapper;
import com.food_store.backend.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    private final ICategoriaRepository iCategoriaRepository;

    @Autowired
    public CategoriaService(ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
    }

    @Override
    public List<Categoria> listarCategoria() {

        return iCategoriaRepository.findAll();
    }

    @Override
    public Categoria crearCategoria(CategoriaCreateDto categoriaCreateDto) {

        Categoria categoriaCreate = CategoriaMapper.toEntity(categoriaCreateDto);
        iCategoriaRepository.save(categoriaCreate);

        return categoriaCreate;
    }
}
