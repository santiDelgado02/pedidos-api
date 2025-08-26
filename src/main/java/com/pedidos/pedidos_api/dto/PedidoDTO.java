package com.pedidos.pedidos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PedidoDTO {

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;

    // Getters y setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
