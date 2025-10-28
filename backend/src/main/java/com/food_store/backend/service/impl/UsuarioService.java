package com.food_store.backend.service.impl;

import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioCreateDto;

import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioLoginDto;
import com.food_store.backend.entity.mapper.UsuarioMapper;
import com.food_store.backend.service.IUsuarioService;
import com.food_store.backend.utils.PasswordUtils;
import com.food_store.backend.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository iUsuarioRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
    }

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return iUsuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioCreateDto usuarioCreateDto) {

        if (iUsuarioRepository.existsByEmail(usuarioCreateDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ya registrado con el email: " + usuarioCreateDto.getEmail());
        }

        Usuario usuarioCreate = UsuarioMapper.toEntity(usuarioCreateDto);
        usuarioCreate.setPassword(PasswordUtils.hashPassword(usuarioCreateDto.getPassword()));
        iUsuarioRepository.save(usuarioCreate);

        return UsuarioMapper.toDto(usuarioCreate);
    }

    @Override
    public UsuarioDto buscarPorId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID no puede ser nulo"
            );
        }
        Usuario usuarioSearch = iUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con ID " + id + " no encontrado"
                ));
        return UsuarioMapper.toDto(usuarioSearch);
    }

    @Override
    public void eliminarUsuarioPorId(Long id) {
        UsuarioDto usuarioDedete = buscarPorId(id);
        iUsuarioRepository.deleteById(usuarioDedete.getId());
    }

    @Override
    public UsuarioDto buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El email no puede ser nulo ni vacío"
            );
        }
        Usuario usuario = iUsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con email \"" + email + "\" no encontrado"
                ));
        return UsuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto login(UsuarioLoginDto usuarioLoginDto){
        Usuario usuario = iUsuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con email \"" + usuarioLoginDto.getEmail() + "\" no encontrado"
                ));

        if (!PasswordUtils.hashPassword(usuarioLoginDto.getPassword()).equals(usuario.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Contraseña incorrecta"
            );
        }

        return UsuarioMapper.toDto(usuario);
    }
}
