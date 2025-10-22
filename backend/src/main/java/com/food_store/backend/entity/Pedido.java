package com.food_store.backend.entity;

import com.food_store.backend.entity.enums.Estado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedidos")
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_pedido") // genera la FK en la tabla detalle_orden
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    private LocalDate fecha;
    private Estado estado;

    private double total;
    // Calculo automatico del total antes de guardar o actualizar
    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        if (detalles == null || detalles.isEmpty()) {
            this.total = 0.0;
        } else {
            this.total = detalles.stream()
                    .mapToDouble(DetallePedido::getSubTotal)
                    .sum();
        }
    }
}
