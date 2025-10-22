package com.food_store.backend.entity;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "detalle_pedido")
@Entity
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Metodos auxiliares en caso de que producto se actualice

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        recalcularSubTotal();
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        recalcularSubTotal();
    }

    private void recalcularSubTotal() {
        if (producto != null && cantidad != null) {
            this.subTotal = producto.getPrecio() * cantidad;
        } else {
            this.subTotal = 0.0;
        }
    }

}
