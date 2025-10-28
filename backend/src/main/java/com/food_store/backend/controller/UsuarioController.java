package com.food_store.backend.controller;

import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioCreateDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioLoginDto;
import com.food_store.backend.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final IUsuarioService iUsuarioService;

    public UsuarioController(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    @GetMapping("/listarUsuarios")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<UsuarioDto> usuarios = iUsuarioService.listarUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK); //200

        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreateDto usuarioCreateDto) {
        try {
            UsuarioDto response = iUsuarioService.crearUsuario(usuarioCreateDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); //201
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            UsuarioDto usuario = iUsuarioService.buscarPorId(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminarPorId/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id) {
        try {
            iUsuarioService.eliminarUsuarioPorId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/buscarPorEmail/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        try {
            UsuarioDto usuarioDto = iUsuarioService.buscarPorEmail(email);
            return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
            UsuarioDto usuarioDto = iUsuarioService.login(usuarioLoginDto);
            return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
