package com.pedidos.pedidos_api.controller;

import com.pedidos.pedidos_api.model.Transportista;
import com.pedidos.pedidos_api.service.TransportistaService;
import com.pedidos.pedidos_api.dto.TransportistaDTO;
import com.pedidos.pedidos_api.dto.TransportistaUpdateDTO;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportistas")
public class TransportistaController {

    private final TransportistaService transportistaService;

    public TransportistaController(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    // GET /api/transportistas
    @GetMapping
    public ResponseEntity<List<Transportista>> getAll() {
        return ResponseEntity.ok(transportistaService.getAll());
    }

    // GET /api/transportistas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Transportista> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transportistaService.getById(id));
    }

    // POST /api/transportistas
    @PostMapping
    public ResponseEntity<Transportista> create(@Valid @RequestBody TransportistaDTO dto) {
        Transportista transportista = transportistaService.create(dto);
        return ResponseEntity.ok(transportista);
    }

    // PUT /api/transportistas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Transportista> update(
        @PathVariable Long id,
        @RequestBody TransportistaUpdateDTO dto
    ) {
        Transportista updated = transportistaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/transportistas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transportistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
