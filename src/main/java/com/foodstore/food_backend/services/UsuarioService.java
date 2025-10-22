package com.foodstore.food_backend.services;

import com.foodstore.food_backend.dtos.UsuarioDTO;
import com.foodstore.food_backend.entities.Usuario;
import com.foodstore.food_backend.enums.Rol;
import com.foodstore.food_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setMail(usuario.getMail());
        dto.setCelular(usuario.getCelular());
        dto.setRol(usuario.getRol());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setMail(dto.getMail());
        entity.setCelular(dto.getCelular());
        entity.setRol(Rol.USUARIO);
        entity.setContrasena(dto.getContrasena());
        return entity;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDto) {
        // Lógica de negocio: Validar campos, chequear duplicados de mail
        Usuario usuario = toEntity(usuarioDto);
        String rawPassword = usuarioDto.getContrasena();
        String contrasenaCodificada = passwordEncoderService.encode(rawPassword);
        usuario.setContrasena(contrasenaCodificada);
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
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setMail(usuarioDto.getMail());
            usuario.setCelular(usuarioDto.getCelular());
            usuario.setRol(usuarioDto.getRol());
            return toDTO(usuarioRepository.save(usuario));
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualizar con ID: " + id));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO loginUsuario(String email, String rawPassword) {

        // 1. Buscar el usuario por email
        Optional<Usuario> optionalUsuario = usuarioRepository.findByMail(email);

        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Credenciales inválidas (Usuario no encontrado)");
        }

        Usuario usuario = optionalUsuario.get();

        // 2. Obtener la contraseña hasheada almacenada en la BD
        String storedHashedPassword = usuario.getContrasena();

        // 3. CRÍTICO: Matchear (comparar) la contraseña plana con la hasheada
        boolean passwordMatches = passwordEncoderService.matches(rawPassword, storedHashedPassword);

        if (!passwordMatches) {
            // Las contraseñas no coinciden
            throw new RuntimeException("Credenciales inválidas (Contraseña incorrecta)");
        }

        // 4. Autenticación exitosa: devolver el DTO del usuario
        return toDTO(usuario);
    }

}