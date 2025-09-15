package com.pedidos.pedidos_api.controller;

import com.pedidos.pedidos_api.dto.EstadoDTO;
import com.pedidos.pedidos_api.dto.PedidoDTO;
import com.pedidos.pedidos_api.dto.PedidoItemDTO;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // ========================
    // LISTAR TODOS LOS PEDIDOS
    // ========================
    @Operation(summary = "Listar todos los pedidos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listado de pedidos",
                content = @Content(schema = @Schema(implementation = Pedido.class)))
        }
    )
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    // ========================
    // OBTENER PEDIDO POR ID
    // ========================
    @Operation(summary = "Obtener un pedido por id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ========================
    // CREAR NUEVO PEDIDO
    // ========================
    @Operation(summary = "Crear un nuevo pedido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente",
                content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.createPedidoFromDTO(pedidoDTO));
    }

    // ========================
    // ACTUALIZAR PEDIDO COMPLETO
    // ========================
    @Operation(summary = "Actualizar un pedido existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente",
                content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoActualizado = pedidoService.updatePedidoFromDTO(id, pedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // ========================
    // ACTUALIZAR SOLO ESTADO
    // ========================
    @Operation(summary = "Actualizar solo el estado de un pedido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente",
                content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> updatePedidoStatus(@PathVariable Long id, @Valid @RequestBody EstadoDTO estadoDTO) {
        Pedido actualizado = pedidoService.updatePedidoStatus(id, estadoDTO.getEstado());
        return ResponseEntity.ok(actualizado);
    }

    // ========================
    // ELIMINAR PEDIDO
    // ========================
    @Operation(summary = "Eliminar un pedido",
        responses = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    // ========================
    // AGREGAR ITEM A PEDIDO
    // ========================
    @Operation(summary = "Agregar un item a un pedido existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Item agregado correctamente",
                content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido o producto no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @PatchMapping("/{id}/items")
    public ResponseEntity<PedidoDTO> addItem(@PathVariable Long id, @Valid @RequestBody PedidoItemDTO itemDTO) {
        PedidoDTO actualizado = pedidoService.addItem(id, itemDTO);
        return ResponseEntity.ok(actualizado);
    }

    // ========================
    // ELIMINAR ITEM DE PEDIDO
    // ========================
    @Operation(summary = "Eliminar un item de un pedido existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Item eliminado correctamente",
                content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido o producto no encontrado",
                content = @Content(schema = @Schema(implementation = Map.class)))
        }
    )
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<PedidoDTO> removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        PedidoDTO actualizado = pedidoService.removeItem(id, itemId);
        return ResponseEntity.ok(actualizado);
    }
}
