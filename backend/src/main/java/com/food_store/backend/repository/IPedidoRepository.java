package com.food_store.backend.repository;

import com.food_store.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
}
