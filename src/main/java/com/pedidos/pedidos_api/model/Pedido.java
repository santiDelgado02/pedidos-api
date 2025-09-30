package com.pedidos.pedidos_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//Le dice a JPA que esta clase representa una tabla en la base de datos.
@Entity
//Si no pones @Table, JPA crea la tabla con el nombre de la clase (Pedido) por defecto
@Table(name = "pedido")

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "transportista_id")
    private Transportista transportista;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PedidoItem> items;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    
    
    
    // Getters y Setters
    public Long getId() { return id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<PedidoItem> getItems() { return items; }
    public void setItems(List<PedidoItem> items) {
        this.items.clear();
        total = BigDecimal.ZERO;
        if (items != null) {
            for (PedidoItem item : items) {
                addItem(item);
            }
        }
    }

    public void addItem(PedidoItem item) {
        if (item == null) return;
        items.add(item);
        item.setPedido(this);       // asegura relación bidireccional
        total = total.add(item.getSubtotal());
    }  

    public void removeItem(PedidoItem item) {
        if (items.remove(item)) {
            item.setPedido(null);
            recalcularTotal();
        }
    }

    private void recalcularTotal() {
        total = BigDecimal.ZERO;
        for (PedidoItem item : items) {
            total = total.add(item.getSubtotal());
        }
    }


    public Transportista getTransportista() { return transportista; }
    public void setTransportista(Transportista transportista) { this.transportista = transportista; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

