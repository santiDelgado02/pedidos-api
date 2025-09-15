package com.pedidos.pedidos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.pedidos.pedidos_api.model.Estado;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PedidoDTO {

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;

    @Schema(description = "Estado del pedido", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "ASIGNADO", "ENTREGADO", "CANCELADO"})
    @NotNull(message = "El estado no puede estar vacío")
    private Estado estado;

    @NotEmpty(message = "Debe incluir al menos un producto")
    @Schema(description = "Lista de productos con cantidad")
    private List<PedidoItemDTO> items;

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

    public List<PedidoItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PedidoItemDTO> items) {
        this.items = items;
    }
}