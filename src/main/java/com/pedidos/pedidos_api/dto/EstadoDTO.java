package com.pedidos.pedidos_api.dto;

import jakarta.validation.constraints.NotBlank;

public class EstadoDTO {

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    // Getter
    public String getEstado() {
        return estado;
    }

    // Setter
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
