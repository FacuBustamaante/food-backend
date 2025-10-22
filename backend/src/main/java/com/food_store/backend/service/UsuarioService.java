package com.food_store.backend.service;

import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.dto.UsuarioCreateDto;

import com.food_store.backend.entity.mapper.UsuarioMapper;
import com.food_store.backend.impl.PasswordUtils;
import com.food_store.backend.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService{

    private final IUsuarioRepository iUsuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository iUsuarioRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Usuario crearUsuario(UsuarioCreateDto usuarioCreateDto) {
        Usuario usuarioCreate = UsuarioMapper.toEntity(usuarioCreateDto);
        usuarioCreate.setPassword(PasswordUtils.hashPassword(usuarioCreateDto.getPassword()));
        iUsuarioRepository.save(usuarioCreate);
        return usuarioCreate;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id){
        if (id == null) return null;
        return iUsuarioRepository.findById(id);
    }


}
