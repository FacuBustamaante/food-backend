package com.food_store.backend.service;

import com.food_store.backend.entity.Pedido;
import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.dto.UsuarioCreateDto;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> listarUsuarios();
    Usuario crearUsuario(UsuarioCreateDto usuarioCreateDto);
    Optional<Usuario> buscarPorId(Long id);
}
