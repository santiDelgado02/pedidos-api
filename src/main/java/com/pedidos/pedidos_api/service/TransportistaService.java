package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.model.Transportista;
import com.pedidos.pedidos_api.repository.TransportistaRepository;
import com.pedidos.pedidos_api.dto.TransportistaDTO;
import com.pedidos.pedidos_api.dto.TransportistaUpdateDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransportistaService {

    private final TransportistaRepository repository;

    public TransportistaService(TransportistaRepository repository) {
        this.repository = repository;
    }

    // Obtener todos
    public List<Transportista> getAll() {
        return repository.findAll();
    }

    // Obtener por id
    public Transportista getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado con id " + id));
    }

    // Crear
    @Transactional
    public Transportista create(TransportistaDTO dto) {
        // Forzamos activo = true al crear
        Transportista t = new Transportista();
        t.setNombre(dto.getNombre());
        t.setCuit(dto.getCuit());
        t.setTelefono(dto.getTelefono());
        t.setEmail(dto.getEmail());
        t.setActivo(true);

        return repository.save(t);
    }

    // Actualizar
    @Transactional
    public Transportista update(Long id, TransportistaUpdateDTO  dto) {
        Transportista t = getById(id);

        if (dto.getNombre() != null) t.setNombre(dto.getNombre());
        if (dto.getTelefono() != null) t.setTelefono(dto.getTelefono());
        if (dto.getEmail() != null) t.setEmail(dto.getEmail());
        if (dto.getActivo() != null) t.setActivo(dto.getActivo());

        return repository.save(t);
    }

    // Eliminar
    @Transactional
    public void delete(Long id) {
        Transportista t = getById(id);
        repository.delete(t);
    }
}