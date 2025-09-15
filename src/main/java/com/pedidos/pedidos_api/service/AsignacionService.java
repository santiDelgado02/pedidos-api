package com.pedidos.pedidos_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidos.pedidos_api.dto.AsignacionDTO;
import com.pedidos.pedidos_api.model.Asignacion;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.model.Transportista;
import com.pedidos.pedidos_api.repository.AsignacionRepository;
import com.pedidos.pedidos_api.repository.PedidoRepository;
import com.pedidos.pedidos_api.repository.TransportistaRepository;

import jakarta.transaction.Transactional;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Transactional
    public AsignacionDTO crearAsignacion(Long pedidoId, Long transportistaId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));

        Asignacion asignacion = new Asignacion();
        asignacion.setPedido(pedido);
        asignacion.setTransportista(transportista);
        asignacion.setEstado(Estado.PENDIENTE);

        Asignacion saved = asignacionRepository.save(asignacion);
        return mapToDTO(saved);
    }

    @Transactional
    public AsignacionDTO actualizarEstado(Long asignacionId, Estado estado) {
        Asignacion asignacion = asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        asignacion.setEstado(estado);

        // Opcional: actualizar estado del pedido si alguien acepta
        if (estado == Estado.ACEPTADO) {
            asignacion.getPedido().setEstado(Estado.ASIGNADO);
        }

        Asignacion saved = asignacionRepository.save(asignacion);
        return mapToDTO(saved);
    }

    public List<AsignacionDTO> listarPorPedido(Long pedidoId) {
        return asignacionRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public AsignacionDTO asignarPedidoATransportista(Long pedidoId, Long transportistaId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Transportista transportista = transportistaRepository.findById(transportistaId)
            .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));

        // Verifica si ya existe la asignación para no duplicar
        boolean existe = asignacionRepository.existsByPedidoIdAndTransportistaId(pedidoId, transportistaId);
        if (existe) {
            throw new RuntimeException("El transportista ya está asignado a este pedido");
        }

        Asignacion asignacion = new Asignacion();
        asignacion.setPedido(pedido);
        asignacion.setTransportista(transportista);
        asignacion.setEstado(Estado.PENDIENTE);

        Asignacion saved = asignacionRepository.save(asignacion);
        return mapToDTO(saved);
    }

    private AsignacionDTO mapToDTO(Asignacion asignacion) {
        AsignacionDTO dto = new AsignacionDTO();
        dto.setId(asignacion.getId());
        dto.setPedidoId(asignacion.getPedido().getId());
        dto.setTransportistaId(asignacion.getTransportista().getId());
        dto.setEstado(asignacion.getEstado().name());
        dto.setFechaAsignacion(asignacion.getFechaAsignacion());
        return dto;
    }
}
