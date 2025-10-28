package com.food_store.backend.controller;

import com.food_store.backend.entity.dto.categoriaDtos.CategoriaCreateDto;
import com.food_store.backend.entity.dto.categoriaDtos.CategoriaDto;
import com.food_store.backend.service.ICategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final ICategoriaService iCategoriaService;

    public CategoriaController(ICategoriaService iCategoriaService) {
        this.iCategoriaService = iCategoriaService;
    }

    //Listar categorias
    @GetMapping("/listarCategorias")
    public ResponseEntity<?> listarCategorias() {
        try {
            List<CategoriaDto> categorias = iCategoriaService.listarCategoria();
            return new ResponseEntity<>(categorias, HttpStatus.OK); //200

        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    //Crear categoria
    @PostMapping("/crearCategorias")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaCreateDto categoriaCreateDto) {
        try {
            CategoriaDto response = iCategoriaService.crearCategoria(categoriaCreateDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); //201
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

    @GetMapping("/buscarCategoria/{categoria}")
    public ResponseEntity<?> buscarCategoria(@PathVariable String categoria) {
        try {
            CategoriaDto response = iCategoriaService.buscarCategoria(categoria);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

    @GetMapping("/buscarCategoriaPorId/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {
        try {
            CategoriaDto response = iCategoriaService.buscarCategoriaPorId(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

    @DeleteMapping("/eliminarCategoria/{categoria}")
    @Transactional
    public ResponseEntity<?> eliminarCategoria(@PathVariable String categoria) {
        try {
            iCategoriaService.eliminarCategoria(categoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
