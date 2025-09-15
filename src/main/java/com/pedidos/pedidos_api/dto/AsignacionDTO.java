package com.pedidos.pedidos_api.dto;

import java.time.LocalDateTime;

public class AsignacionDTO {

    private Long id;
    private Long pedidoId;
    private Long transportistaId;
    private String estado; // PENDIENTE, ACEPTADO, RECHAZADO, CANCELADO
    private LocalDateTime fechaAsignacion;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
}