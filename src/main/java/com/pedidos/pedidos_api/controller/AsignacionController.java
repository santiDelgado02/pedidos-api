package com.pedidos.pedidos_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.pedidos_api.dto.AsignacionDTO;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.service.AsignacionService;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @PostMapping
    public ResponseEntity<AsignacionDTO> crear(@RequestParam Long pedidoId,
                                               @RequestParam Long transportistaId) {
        return ResponseEntity.ok(asignacionService.crearAsignacion(pedidoId, transportistaId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AsignacionDTO> actualizarEstado(@PathVariable Long id,
                                                          @RequestParam Estado estado) {
        return ResponseEntity.ok(asignacionService.actualizarEstado(id, estado));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<AsignacionDTO>> listarPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(asignacionService.listarPorPedido(pedidoId));
    }

    @PostMapping("/asignar")
    public ResponseEntity<AsignacionDTO> asignarPedido(
            @RequestParam Long pedidoId,
            @RequestParam Long transportistaId) {
        return ResponseEntity.ok(
            asignacionService.asignarPedidoATransportista(pedidoId, transportistaId)
        );
    }
}
