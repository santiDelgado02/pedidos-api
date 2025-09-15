package com.pedidos.pedidos_api.repository;

import com.pedidos.pedidos_api.model.Asignacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    List<Asignacion> findByPedidoId(Long pedidoId);
    List<Asignacion> findByTransportistaId(Long transportistaId);
    boolean existsByPedidoIdAndTransportistaId(Long pedidoId, Long transportistaId);
}
