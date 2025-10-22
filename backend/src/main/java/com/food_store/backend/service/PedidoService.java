package com.food_store.backend.service;

import com.food_store.backend.entity.*;
import com.food_store.backend.entity.dto.DetalleRequestDto;
import com.food_store.backend.entity.dto.PedidoCreateDto;
import com.food_store.backend.entity.enums.Estado;
import com.food_store.backend.entity.mapper.DetallePedidoMapper;
import com.food_store.backend.entity.mapper.PedidoMapper;
import com.food_store.backend.repository.IPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService implements IPedidoService{

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
        for (DetalleRequestDto detalle : pedidoCreateDto.getDetalles()) {
            Producto producto = iProductoService.buscarPorId(detalle.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            DetallePedido detalleCreate = DetallePedidoMapper.toEntity(detalle);
            detalleCreate.setProducto(producto);
            pedidoCreate.getDetalles().add(detalleCreate);
        }
        Usuario usuario = iUsuarioService.buscarPorId(pedidoCreateDto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        pedidoCreate.setUsuario(usuario);
        iPedidoRepository.save(pedidoCreate);

        return pedidoCreate ;
    };



}
