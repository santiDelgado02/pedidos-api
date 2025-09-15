package com.pedidos.pedidos_api.dto;

import com.pedidos.pedidos_api.model.Estado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class EstadoDTO {

    @NotNull(message = "El estado no puede estar vacío")
    @Schema(description = "Estado del pedido",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "ASIGNADO", "ENTREGADO", "CANCELADO"})
    private Estado estado;

    // Getter
    public Estado getEstado() {
        return estado;
    }

    // Setter
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
