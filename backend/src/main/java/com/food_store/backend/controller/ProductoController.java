package com.food_store.backend.controller;
import com.food_store.backend.entity.Producto;
import com.food_store.backend.entity.dto.productoDtos.ProductoCreateDto;
import com.food_store.backend.entity.dto.productoDtos.ProductoDto;
import com.food_store.backend.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final IProductoService iProductoService;

    public ProductoController(IProductoService iProductoService) {
        this.iProductoService = iProductoService;
    }

    @GetMapping("/listarProductos")
    public ResponseEntity<?> listarProductos() {
        try {
            List<ProductoDto> prductos = iProductoService.listarProductos();
            return new ResponseEntity<>(prductos, HttpStatus.OK); //200

        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            ProductoDto response = iProductoService.buscarPorId(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

    @PostMapping("/crearProducto")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoCreateDto productoCreateDto) {
        try{
            ProductoDto response = iProductoService.crearProducto(productoCreateDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }

    }
    @DeleteMapping("/eliminarPorId/{id}")
    public ResponseEntity<?> eliminarProductoPorId(@PathVariable Long id){
        try {
            iProductoService.eliminarProducto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//400
        }
    }

}
