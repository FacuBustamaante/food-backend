package com.foodstore.food_backend.repositories;
import com.foodstore.food_backend.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long id);
}
