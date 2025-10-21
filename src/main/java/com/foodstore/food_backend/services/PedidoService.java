package com.foodstore.food_backend.services;
import com.foodstore.food_backend.dtos.DetallePedidoCreacionDTO;
import com.foodstore.food_backend.dtos.DetallePedidoDTO;
import com.foodstore.food_backend.dtos.PedidoCreacionDTO;
import com.foodstore.food_backend.dtos.PedidoDTO;
import com.foodstore.food_backend.entities.DetallePedido;
import com.foodstore.food_backend.entities.Pedido;
import com.foodstore.food_backend.entities.Producto;
import com.foodstore.food_backend.entities.Usuario;
import com.foodstore.food_backend.enums.Estado;
import com.foodstore.food_backend.repositories.DetallePedidoRepository;
import com.foodstore.food_backend.repositories.PedidoRepository;
import com.foodstore.food_backend.repositories.ProductoRepository;
import com.foodstore.food_backend.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private DetallePedidoDTO toDetalleDTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
            dto.setNombreProducto(detalle.getProducto().getNombre());
            dto.setPrecioUnitario(detalle.getProducto().getPrecio());
        }
        return dto;
    }

    private PedidoDTO toPedidoDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());

        if (pedido.getUsuario() != null) {
            dto.setUsuarioId(pedido.getUsuario().getId());
            dto.setNombreCompletoUsuario(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
        }

        if (pedido.getDetalles() != null) {
            dto.setDetalles(pedido.getDetalles().stream()
                    .map(this::toDetalleDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional
    public PedidoDTO crearPedido(PedidoCreacionDTO dto) {
        // 1. Obtener Usuario
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Estado.PENDIENTE);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        Double totalCalculado = 0.0;

        List<DetallePedido> detallesCreados = new ArrayList<>();

        // 3. Procesar Detalles
        for (DetallePedidoCreacionDTO detalleDto : dto.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDto.getCantidad());

            Double subtotal = producto.getPrecio() * detalleDto.getCantidad();
            detalle.setSubtotal(subtotal);

            DetallePedido detalleGuardado = detallePedidoRepository.save(detalle);
            totalCalculado += subtotal;

            detallesCreados.add(detalleGuardado);
        }

        pedidoGuardado.setDetalles(detallesCreados);

        // 4. Actualizar Total y Guardar
        pedidoGuardado.setTotal(totalCalculado);

        return toPedidoDTO(pedidoRepository.save(pedidoGuardado));
    }

    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::toPedidoDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(this::toPedidoDTO)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional
    public PedidoDTO actualizarEstadoPedido(Long id, Estado nuevoEstado) {
        return pedidoRepository.findById(id).map(pedido -> {
            // Lógica de negocio: Validar transiciones de estado (ej. No se puede pasar de TERMINADO a PENDIENTE)
            if (pedido.getEstado().equals(Estado.CANCELADO) || pedido.getEstado().equals(Estado.TERMINADO)) {
                throw new RuntimeException("No se puede modificar el estado de un pedido ya finalizado o cancelado.");
            }
            pedido.setEstado(nuevoEstado);
            return toPedidoDTO(pedidoRepository.save(pedido));
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado para actualizar estado con ID: " + id));
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}