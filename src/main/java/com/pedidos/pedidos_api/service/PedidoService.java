package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    // Inyección de dependencias (Spring lo hace automático)
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // CREATE
    public Pedido createPedido(Pedido pedido) {
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

    // UPDATE - actualizar todo
    public Pedido updatePedido(Long id, Pedido pedidoDetails) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setDescripcion(pedidoDetails.getDescripcion());
            pedido.setEstado(pedidoDetails.getEstado());
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    // PATCH - actualizar solo estado
    public Pedido updatePedidoStatus(Long id, String estado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(estado);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    // DELETE
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
