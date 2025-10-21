package com.foodstore.food_backend.controllers;
import com.foodstore.food_backend.dtos.PedidoCreacionDTO;
import com.foodstore.food_backend.dtos.PedidoDTO;
import com.foodstore.food_backend.enums.Estado;
import com.foodstore.food_backend.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoCreacionDTO pedidoDto) {
        try {
            PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoDto);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Captura de errores como "Usuario no encontrado" o "Producto no encontrado"
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        try {
            PedidoDTO pedido = pedidoService.obtenerPedidoPorId(id);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(@PathVariable Long id, @RequestParam Estado estado) {
        try {
            PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            // NOT_FOUND si el pedido no existe, BAD_REQUEST si la transición es inválida
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}