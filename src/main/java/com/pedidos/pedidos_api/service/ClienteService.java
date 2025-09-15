package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.dto.ClienteDTO;
import com.pedidos.pedidos_api.model.Cliente;
import com.pedidos.pedidos_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // ==== Métodos CRUD ====

    public Cliente saveCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> getAllClientes() {
        return repository.findAll();
    }

    public Cliente getClienteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void deleteCliente(Long id) {
        Cliente cliente = getClienteById(id);
        repository.delete(cliente);
    }

    // ==== Métodos para DTO ====

    /** Crear un cliente nuevo */
    public Cliente createFromDTO(ClienteDTO dto) {
        // Validar email único
        repository.findByEmail(dto.getEmail())
                .ifPresent(c -> { throw new RuntimeException("Email ya existe en otro cliente"); });

        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());        // Solo al crear se asigna el email
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return saveCliente(cliente);
    }

    /** Actualizar campos editables de un cliente existente (no se modifica email) */
    public Cliente updateFromDTO(Long id, ClienteDTO dto) {
        Cliente cliente = getClienteById(id);

        if (dto.getNombre() != null) {
            cliente.setNombre(dto.getNombre());
        }
        if (dto.getTelefono() != null) {
            cliente.setTelefono(dto.getTelefono());
        }
        if (dto.getDireccion() != null) {
            cliente.setDireccion(dto.getDireccion());
        }
        if (dto.getActivo() != null) {
            cliente.setActivo(dto.getActivo());
        }

        // Email no se toca en updates
        return saveCliente(cliente);
    }
}
