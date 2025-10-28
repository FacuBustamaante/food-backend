package com.food_store.backend.service;

import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioCreateDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioLoginDto;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDto> listarUsuarios();
    UsuarioDto crearUsuario(UsuarioCreateDto usuarioCreateDto);
    UsuarioDto buscarPorId(Long id);
    void eliminarUsuarioPorId(Long id);
    UsuarioDto buscarPorEmail(String email);
    UsuarioDto login(UsuarioLoginDto usuarioLoginDto);
}
