package com.pedidos.pedidos_api.controller;

import com.pedidos.pedidos_api.dto.ClienteDTO;
import com.pedidos.pedidos_api.interfaces.ClienteValidationGroups.OnCreate;
import com.pedidos.pedidos_api.interfaces.ClienteValidationGroups.OnUpdate;
import com.pedidos.pedidos_api.model.Cliente;
import com.pedidos.pedidos_api.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // === Crear ===
    @PostMapping
    public ResponseEntity<Cliente> createCliente(
            @Validated(OnCreate.class) @RequestBody ClienteDTO dto) {
        Cliente creado = service.createFromDTO(dto);
        return ResponseEntity.ok(creado);
    }

    // === Obtener todos ===
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(service.getAllClientes());
    }

    // === Obtener por id ===
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getClienteById(id));
    }

    // === Actualizar ===
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody ClienteDTO dto) {
        Cliente actualizado = service.updateFromDTO(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // === Eliminar ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        service.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
