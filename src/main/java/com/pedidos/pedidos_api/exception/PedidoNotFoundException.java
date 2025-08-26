package com.pedidos.pedidos_api.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(Long id) {
        super("Pedido con id " + id + " no encontrado");
    }
}
