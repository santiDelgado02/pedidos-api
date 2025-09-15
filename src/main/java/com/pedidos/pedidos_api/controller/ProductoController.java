package com.pedidos.pedidos_api.controller;

import com.pedidos.pedidos_api.dto.ProductoDTO;
import com.pedidos.pedidos_api.model.Producto;
import com.pedidos.pedidos_api.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 📌 Crear producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto producto = productoService.createProducto(dto);
        return ResponseEntity.ok(producto);
    }

    // 📌 Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    // 📌 Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    // 📌 Actualizar producto
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id,
                                                   @Valid @RequestBody ProductoDTO dto) {
        Producto producto = productoService.updateProducto(id, dto);
        return ResponseEntity.ok(producto);
    }

    // 📌 Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
