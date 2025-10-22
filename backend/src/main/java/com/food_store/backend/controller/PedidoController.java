package com.food_store.backend.controller;

import com.food_store.backend.entity.Pedido;
import com.food_store.backend.entity.dto.PedidoCreateDto;
import com.food_store.backend.entity.dto.PedidoDto;
import com.food_store.backend.entity.mapper.PedidoMapper;
import com.food_store.backend.service.IPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {


    private final IPedidoService iPedidoService;

    public PedidoController(IPedidoService iPedidoService) {
        this.iPedidoService = iPedidoService;
    }

    @PostMapping("/crearPedido")

    public ResponseEntity<PedidoDto> crearPedido(@RequestBody PedidoCreateDto pedidoCreateDto){

        Pedido response = iPedidoService.crearPedido(pedidoCreateDto);
        PedidoDto responseDto = PedidoMapper.toDto(response);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
