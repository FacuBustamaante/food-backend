package com.foodstore.food_backend.repositories;
import com.foodstore.food_backend.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);
    Optional<Producto> findById(Long id);
}
