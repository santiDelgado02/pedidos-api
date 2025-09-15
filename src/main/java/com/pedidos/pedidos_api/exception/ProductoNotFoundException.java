package com.pedidos.pedidos_api.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Producto con id " + id + " no encontrado");
    }
}