package com.food_store.backend.controller;

import com.food_store.backend.entity.Categoria;
import com.food_store.backend.entity.Usuario;
import com.food_store.backend.entity.dto.CategoriaCreateDto;
import com.food_store.backend.entity.dto.CategoriaDto;
import com.food_store.backend.entity.dto.UsuarioCreateDto;
import com.food_store.backend.entity.dto.UsuarioDto;
import com.food_store.backend.entity.mapper.CategoriaMapper;
import com.food_store.backend.entity.mapper.UsuarioMapper;
import com.food_store.backend.service.IProductoService;
import com.food_store.backend.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final IUsuarioService iUsuarioService;

    public UsuarioController(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    @GetMapping("/listarUsuarios")
    public List<UsuarioDto> listarUsuarios(){

        return iUsuarioService.listarUsuarios().stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping("/crearUsuario")
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody UsuarioCreateDto usuarioCreateDto){
        Usuario response = iUsuarioService.crearUsuario(usuarioCreateDto);
        UsuarioDto responseDto = UsuarioMapper.toDto(response);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

}
