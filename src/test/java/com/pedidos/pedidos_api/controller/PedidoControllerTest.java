package com.pedidos.pedidos_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedidos.pedidos_api.dto.PedidoDTO;
import com.pedidos.pedidos_api.model.Estado;
import com.pedidos.pedidos_api.model.Pedido;
import com.pedidos.pedidos_api.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    void testCreatePedido() throws Exception {
        PedidoDTO dto = new PedidoDTO();
        dto.setDescripcion("Test Pedido");
        dto.setEstado(Estado.PENDIENTE);

        Pedido pedido = new Pedido();
        pedido.setDescripcion("Test Pedido");
        pedido.setEstado(Estado.PENDIENTE);

        when(pedidoService.createPedidoFromDTO(any(PedidoDTO.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Test Pedido"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(pedidoService, times(1)).createPedidoFromDTO(any(PedidoDTO.class));
    }
}
