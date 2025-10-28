package com.food_store.backend.service.impl;

import com.food_store.backend.entity.*;
import com.food_store.backend.entity.dto.DetallePedidoDtos.DetalleRequestDto;
import com.food_store.backend.entity.dto.PedidoDtos.PedidoCreateDto;
import com.food_store.backend.entity.dto.UsuarioDtos.UsuarioDto;
import com.food_store.backend.entity.enums.Estado;
import com.food_store.backend.entity.mapper.DetallePedidoMapper;
import com.food_store.backend.entity.mapper.PedidoMapper;
import com.food_store.backend.entity.mapper.UsuarioMapper;
import com.food_store.backend.repository.IPedidoRepository;
import com.food_store.backend.service.IPedidoService;
import com.food_store.backend.service.IProductoService;
import com.food_store.backend.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService implements IPedidoService {

    private final IPedidoRepository iPedidoRepository;
    private final IProductoService iProductoService;
    private final IUsuarioService iUsuarioService;

    @Autowired
    public PedidoService(IPedidoRepository iPedidoRepository, IProductoService iProductoService, IUsuarioService iUsuarioService) {
        this.iPedidoRepository = iPedidoRepository;
        this.iProductoService = iProductoService;
        this.iUsuarioService = iUsuarioService;
    }


    public Pedido crearPedido(PedidoCreateDto pedidoCreateDto){

        Pedido pedidoCreate = PedidoMapper.toEntity(pedidoCreateDto);

        String estadoStr = pedidoCreateDto.getEstado();
        if (estadoStr == null || estadoStr.isBlank()) {
            throw new RuntimeException("El campo 'estado' no puede ser nulo");
        }

        pedidoCreate.setEstado(Estado.valueOf(estadoStr.toUpperCase()));

        //Agregamos los detalles

        //ARREGLAR PRODUCTO

//        for (DetalleRequestDto detalle : pedidoCreateDto.getDetalles()) {
//            Producto producto = iProductoService.buscarPorId(detalle.getProductoId())
//                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
//            DetallePedido detalleCreate = DetallePedidoMapper.toEntity(detalle);
//            detalleCreate.setProducto(producto);
//            pedidoCreate.getDetalles().add(detalleCreate);
//        }
        UsuarioDto usuarioDto = iUsuarioService.buscarPorId(pedidoCreateDto.getIdUsuario());

        pedidoCreate.setUsuario(UsuarioMapper.toEntity(usuarioDto));
        iPedidoRepository.save(pedidoCreate);

        return pedidoCreate ;
    };



}
