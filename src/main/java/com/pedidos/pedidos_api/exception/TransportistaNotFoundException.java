package com.pedidos.pedidos_api.exception;

public class TransportistaNotFoundException extends RuntimeException {
    public TransportistaNotFoundException(Long id) {
        super("Transportista con id " + id + " no encontrado");
    }
}
