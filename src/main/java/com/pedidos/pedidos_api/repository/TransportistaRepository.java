package com.pedidos.pedidos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pedidos.pedidos_api.model.Transportista;

public interface TransportistaRepository extends JpaRepository<Transportista, Long> {
    boolean existsByCuit(String cuit);
    boolean existsByEmail(String email);
}