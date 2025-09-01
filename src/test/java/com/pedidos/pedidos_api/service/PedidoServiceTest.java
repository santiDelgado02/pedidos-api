package com.pedidos.pedidos_api.service;

import com.pedidos.pedidos_api.dto.PedidoDTO;
import java.util.Optional;

// JUnit 5
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

// Tus clases del proyecto
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.repository.PedidoRepository;
import com.pedidos.pedidos_api.exception.PedidoNotFoundException;


class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePedidoFromDTO() {
        PedidoDTO dto = new PedidoDTO();
        dto.setDescripcion("Test");
        dto.setEstado(Estado.PENDIENTE);

        Pedido pedido = new Pedido();
        pedido.setDescripcion("Test");
        pedido.setEstado(Estado.PENDIENTE);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.createPedidoFromDTO(dto);

        assertEquals("Test", resultado.getDescripcion());
        assertEquals(Estado.PENDIENTE, resultado.getEstado());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testUpdatePedidoStatusNotFound() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            pedidoService.updatePedidoStatus(1L, Estado.COMPLETADO);
        });
    }
}
