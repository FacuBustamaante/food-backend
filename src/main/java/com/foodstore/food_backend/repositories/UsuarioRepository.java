package com.foodstore.food_backend.repositories;
import com.foodstore.food_backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByMail(String mail);
    Optional<Usuario> findById(Long id);
}
