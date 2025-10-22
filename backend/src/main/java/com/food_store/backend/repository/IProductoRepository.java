package com.food_store.backend.repository;

import com.food_store.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
}
