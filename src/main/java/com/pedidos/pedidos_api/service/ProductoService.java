package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.dto.ProductoDTO;
import com.pedidos.pedidos_api.exception.ProductoNotFoundException;
import com.pedidos.pedidos_api.model.Producto;
import com.pedidos.pedidos_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Crear un producto
    @Transactional
    public Producto createProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre().trim());
        producto.setSku(dto.getSku().trim());
        producto.setPrecio(dto.getPrecio());

        logger.info("Creando producto: {} ({})", dto.getNombre(), dto.getSku());
        return productoRepository.save(producto);
    }

    // Actualizar un producto
    @Transactional
    public Producto updateProducto(Long id, ProductoDTO dto) {
        Producto producto = getProductoById(id);

        if (dto.getNombre() != null) producto.setNombre(dto.getNombre().trim());
        if (dto.getSku() != null) producto.setSku(dto.getSku().trim());
        if (dto.getPrecio() != null) producto.setPrecio(dto.getPrecio());

        logger.info("Actualizando producto con id {}: {}", id, producto.getNombre());
        return productoRepository.save(producto);
    }

    // Traer un producto por id
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // Traer todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Eliminar un producto
    @Transactional
    public void deleteProducto(Long id) {
        Producto producto = getProductoById(id);
        logger.info("Eliminando producto con id {}: {}", id, producto.getNombre());
        productoRepository.delete(producto);
    }
}