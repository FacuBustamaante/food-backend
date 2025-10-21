package com.foodstore.food_backend.services;

import com.foodstore.food_backend.dtos.UsuarioDTO;
import com.foodstore.food_backend.entities.Usuario;
import com.foodstore.food_backend.enums.Rol;
import com.foodstore.food_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- Mappers (Implementación simple in-situ) ---

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setMail(usuario.getMail());
        dto.setCelular(usuario.getCelular());
        dto.setRol(Rol.USUARIO);
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        // En un caso real, el DTO de creación/actualización incluiría la contraseña
        Usuario entity = new Usuario();
        entity.setId(dto.getId()); // Usar solo si es actualización
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setMail(dto.getMail());
        entity.setCelular(dto.getCelular());
        entity.setRol(dto.getRol());
        // Contraseña y Hash de Contraseña se manejarían aquí en una implementación real
        return entity;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDto) {
        // Lógica de negocio: Validar campos, chequear duplicados de mail
        Usuario usuario = toEntity(usuarioDto);
        // NOTA: En un proyecto real, la contraseña debe ser hasheada antes de guardar
        return toDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDto) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Lógica para transferir datos del DTO a la Entidad
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setMail(usuarioDto.getMail());
            usuario.setCelular(usuarioDto.getCelular());
            usuario.setRol(usuarioDto.getRol());
            return toDTO(usuarioRepository.save(usuario));
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualizar con ID: " + id));
    }

    public void eliminarUsuario(Long id) {
        // Lógica de negocio: Chequear si el usuario tiene pedidos activos, etc.
        usuarioRepository.deleteById(id);
    }
}