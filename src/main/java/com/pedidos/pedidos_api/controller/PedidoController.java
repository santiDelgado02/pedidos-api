package com.pedidos.pedidos_api.controller;

import com.pedidos.pedidos_api.dto.EstadoDTO;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET /pedidos → todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    // GET /pedidos/{id} → pedido por id
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /pedidos → crear pedido
    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoService.createPedido(pedido);
    }

    // PUT /pedidos/{id} → actualizar todo el pedido
    @PutMapping("/{id}")
    public Pedido updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        return pedidoService.updatePedido(id, pedidoDetails);
    }

    // PATCH /pedidos/{id}/estado → actualizar solo estado con JSON
    @PatchMapping("/{id}/estado")
    public Pedido updatePedidoStatus(@PathVariable Long id, @RequestBody EstadoDTO estadoDTO) {
        return pedidoService.updatePedidoStatus(id, estadoDTO.getEstado());
    }


    // DELETE /pedidos/{id} → eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
