package com.pedidos.pedidos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.pedidos.pedidos_api.model.Estado;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class PedidoDTO {

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;

    @Schema(description = "Estado del pedido", example = "PENDIENTE", allowableValues = {"PENDIENTE", "EN_PROCESO", "COMPLETADO"})
    @NotNull(message = "El estado no puede estar vacío")
    private Estado estado;

    // Getters y setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
