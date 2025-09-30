package com.pedidos.pedidos_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pedidos.pedidos_api.dto.PedidoDTO;
import com.pedidos.pedidos_api.dto.PedidoItemDTO;
import com.pedidos.pedidos_api.exception.PedidoNotFoundException;
import com.pedidos.pedidos_api.exception.ProductoNotFoundException;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.model.PedidoItem;
import com.pedidos.pedidos_api.model.Producto;
import com.pedidos.pedidos_api.repository.PedidoRepository;
import com.pedidos.pedidos_api.repository.ProductoRepository;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    // ========================
    // CREATE PEDIDO
    // ========================
    @Transactional
    public PedidoDTO createPedidoFromDTO(PedidoDTO dto) {
        validatePedidoDTO(dto);

        Pedido pedido = new Pedido();
        pedido.setDescripcion(dto.getDescripcion());
        pedido.setEstado(dto.getEstado());

        // Convertir cada itemDTO a PedidoItem y agregar al pedido usando setItems/addItem
        List<PedidoItem> items = new ArrayList<>();
    for (PedidoItemDTO itemDTO : dto.getItems()) {
        PedidoItem item = mapItemDTOToPedidoItem(itemDTO, pedido);
            items.add(item);
        }
        pedido.setItems(items); // setItems se encarga de total y relación bidireccional

        Pedido saved = pedidoRepository.save(pedido);

        return mapPedidoToDTO(saved);
    }

    // ========================
    // GET ALL
    // ========================
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // ========================
    // GET BY ID
    // ========================
    public Optional<Pedido> getPedidoById(Long id) {
        return pedidoRepository.findById(id);
    }

    // ========================
    // UPDATE COMPLETO
    // ========================
    @Transactional
    public Pedido updatePedidoFromDTO(Long id, PedidoDTO dto) {
        validatePedidoDTO(dto);

        return pedidoRepository.findById(id).map(pedido -> {
            mapDTOToPedido(dto, pedido);

            // Limpiar items antiguos y agregar nuevos usando setItems
            List<PedidoItem> items = new ArrayList<>();
            for (PedidoItemDTO itemDTO : dto.getItems()) {
                PedidoItem item = mapItemDTOToPedidoItem(itemDTO, pedido);
                items.add(item);
            }
            pedido.setItems(items); // setItems se encarga de total y relación bidireccional

            logger.info("Pedido con id {} actualizado, total={}", id, pedido.getTotal());
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNotFoundException(id));
    }


    // ========================
    // PATCH - SOLO ESTADO
    // ========================
    @Transactional
    public Pedido updatePedidoStatus(Long id, Estado estado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(estado);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNotFoundException(id));
    }

    // ========================
    // DELETE
    // ========================
    @Transactional
    public void deletePedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoNotFoundException(id);
        }
        pedidoRepository.deleteById(id);
    }

    // ========================
    // AGREGAR ITEM
    // ========================
    @Transactional
    public PedidoDTO addItem(Long pedidoId, PedidoItemDTO itemDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNotFoundException(pedidoId));

        Producto producto = productoRepository.findById(itemDTO.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(itemDTO.getProductoId()));

        PedidoItem item = new PedidoItem();
        item.setProducto(producto);
        item.setCantidad(itemDTO.getCantidad());
        item.setPrecioUnitario(producto.getPrecio());
        item.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(itemDTO.getCantidad())));

        pedido.addItem(item);  // Llama al método del modelo

        Pedido saved = pedidoRepository.save(pedido);
        return mapPedidoToDTO(saved);
    }

    // ========================
    // ELIMINAR ITEM
    // ========================
    @Transactional
    public PedidoDTO removeItem(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNotFoundException(pedidoId));

        PedidoItem item = pedido.getItems().stream()
                                .filter(i -> i.getId().equals(itemId))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"));

        pedido.removeItem(item); // 🚀 Llama al método del modelo

        Pedido saved = pedidoRepository.save(pedido);
        return mapPedidoToDTO(saved);
    }

    // ========================
    // AUXILIARES
    // ========================
    private void mapDTOToPedido(PedidoDTO dto, Pedido pedido) {
        if (dto.getDescripcion() != null && !dto.getDescripcion().isBlank()) {
            pedido.setDescripcion(dto.getDescripcion().trim());
        }
        if (dto.getEstado() != null) {
            pedido.setEstado(dto.getEstado());
        }
    }

    private void validatePedidoDTO(PedidoDTO dto) {
        if (!StringUtils.hasText(dto.getDescripcion())) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (dto.getEstado() == null) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un producto");
        }
    }

    private PedidoItem mapItemDTOToPedidoItem(PedidoItemDTO itemDTO, Pedido pedido) {
        Producto producto = productoRepository.findById(itemDTO.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(itemDTO.getProductoId()));

        PedidoItem item = new PedidoItem();
        item.setProducto(producto);
        item.setCantidad(itemDTO.getCantidad());
        item.setPrecioUnitario(producto.getPrecio());

        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(itemDTO.getCantidad()));
        item.setSubtotal(subtotal);
        item.setPedido(pedido);

        return item;
    }

    private PedidoDTO mapPedidoToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setDescripcion(pedido.getDescripcion());
        dto.setEstado(pedido.getEstado());

        List<PedidoItemDTO> itemsDTO = new ArrayList<>();
        for (PedidoItem item : pedido.getItems()) {
            PedidoItemDTO itemDTO = new PedidoItemDTO();
            itemDTO.setProductoId(item.getProducto().getId());
            itemDTO.setCantidad(item.getCantidad());
            itemsDTO.add(itemDTO);
        }
        dto.setItems(itemsDTO);

        return dto;
    }
}
