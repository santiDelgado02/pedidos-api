package com.pedidos.pedidos_api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pedidos.pedidos_api.dto.PedidoDTO;
import com.pedidos.pedidos_api.exception.PedidoNotFoundException;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.repository.PedidoRepository;


@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;

    // Inyección de dependencias (Spring lo hace automático)
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido createPedidoFromDTO(PedidoDTO pedidoDTO) {
        logger.info("Creando un nuevo pedido con descripción: {}", pedidoDTO.getDescripcion());
        Pedido pedido = new Pedido();
        pedido.setDescripcion(pedidoDTO.getDescripcion());
        pedido.setEstado(pedidoDTO.getEstado());
        logger.debug("Pedido mapeado correctamente, listo para guardar en la base de datos");
        return pedidoRepository.save(pedido);
    }

    // READ - traer todos
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // READ - traer uno por id
    public Optional<Pedido> getPedidoById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido updatePedidoFromDTO(Long id, PedidoDTO pedidoDTO) {
       validatePedidoDTO(pedidoDTO);
       return pedidoRepository.findById(id).map(pedido -> {
         mapDTOToPedido(pedidoDTO, pedido);
            logger.info("Actualizando pedido con id {}: {}", id, pedidoDTO.getDescripcion());
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNotFoundException(id));
    }

    // PATCH - actualizar solo estado
    @Transactional
    public Pedido updatePedidoStatus(Long id, Estado estado) {
        logger.info("Actualizando estado del pedido con id {} a '{}'", id, estado);
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(estado);
            logger.debug("Pedido actualizado correctamente");
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> {
            logger.error("Pedido con id {} no encontrado", id);
            return new PedidoNotFoundException(id);
        });
    }


    // DELETE
    @Transactional
    public void deletePedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoNotFoundException(id);
        }
        pedidoRepository.deleteById(id);
    }


    // Métodos auxiliares privados

    private void mapDTOToPedido(PedidoDTO dto, Pedido pedido) {
        // Solo asigna valores no nulos y elimina espacios en blanco
        if (dto.getDescripcion() != null && !dto.getDescripcion().isBlank()) {
            pedido.setDescripcion(dto.getDescripcion().trim());
        }
        if (dto.getEstado() != null) {
            pedido.setEstado(dto.getEstado()); // ahora es enum, se asigna directo
        }
    }


    private void validatePedidoDTO(PedidoDTO dto) {
        // Validación estricta para asegurar datos válidos
        if (!StringUtils.hasText(dto.getDescripcion())) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (dto.getEstado() == null) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
    }
}
