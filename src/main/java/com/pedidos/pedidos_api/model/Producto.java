package com.pedidos.pedidos_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="producto")
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false, unique=true)
    private String sku;

    @Column(nullable=false)
    private BigDecimal precio;

    private Boolean activo = true;


    // getters y setters

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getSku() {
            return sku;
        }
        public void setSku(String sku) {
            this.sku = sku;
        }

        public BigDecimal getPrecio() {
            return precio;
        }
        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public Boolean getActivo() {
            return activo;
        }
        public void setActivo(Boolean activo) {
            this.activo = activo;
        }
}
