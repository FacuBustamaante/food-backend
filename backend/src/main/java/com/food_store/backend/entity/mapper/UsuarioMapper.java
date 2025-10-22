package com.food_store.backend.entity.mapper;

import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.Pedido;
import com.food_store.backend.entity.dto.UsuarioCreateDto;
import com.food_store.backend.entity.dto.UsuarioDto;

import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) ;

        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .role(usuario.getRole())
                .pedidosIds(
                        usuario.getPedidos() == null
                                ? null
                                : usuario.getPedidos()
                                .stream()
                                .map(Pedido::getId_pedido)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static Usuario  toEntity(UsuarioCreateDto usuarioCreateDto){
        if (usuarioCreateDto == null) return null;

        return Usuario.builder()
                .nombre(usuarioCreateDto.getNombre())
                .email(usuarioCreateDto.getEmail())
                .role(usuarioCreateDto.getRole())
                .build();

    }

}
