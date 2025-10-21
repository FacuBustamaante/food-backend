package com.foodstore.food_backend.entities;
import com.foodstore.food_backend.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long id

    private String nombre;
    private String apellido;
    private String mail;
    private int celular;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    // Relación 1..m con Pedido
    // No es necesario mapear la lista de Pedidos aquí si solo se necesita la relación unidireccional desde Pedido,
    // pero la incluyo para completitud:
    // @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pedido> pedidos;
}
