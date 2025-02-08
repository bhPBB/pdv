package com.brunobomfim.pdv;

import com.brunobomfim.pdv.controllers.VendaController;
import com.brunobomfim.pdv.models.*;
import com.brunobomfim.pdv.services.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaService vendaService;

    private Item item;
    private Usuario usuario;
    private Venda venda;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("cliente@email.com", "Cliente Teste", "senha123");
        item = new Item(usuario, "Produto 1", 1L, 10.0, 5);
        venda = new Venda(usuario, 1L, Map.of(item, 2));

    }

    @Test
    void registrarVenda_DeveRetornarVenda() throws Exception {
        when(vendaService.registrarVenda(anyString(), anyMap())).thenReturn(venda);

        mockMvc.perform(post("/vendas/registrar")
                .param("usuarioEmail", "cliente@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void recuperarTodosItens_DeveRetornarListaDeItens() throws Exception {
        when(vendaService.recuperarTodosItens(anyString())).thenReturn(List.of(item));

        mockMvc.perform(get("/vendas/itens/cliente@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"));
    }

    @Test
    void atualizarPrecoItem_DeveRetornarItemAtualizado() throws Exception {
        item.setPreco(15.0);
        when(vendaService.atualizarPrecoItem(any(), anyDouble())).thenReturn(item);

        mockMvc.perform(put("/vendas/item/preco")
                .param("usuarioEmail", "cliente@email.com")
                .param("itemId", "1")
                .param("novoPreco", "15.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(15.0));
    }

    @Test
    void atualizarQuantidadeItem_DeveRetornarItemAtualizado() throws Exception {
        item.setQuantidade(3);
        when(vendaService.atualizarQuantidadeItem(any(), anyInt())).thenReturn(item);

        mockMvc.perform(put("/vendas/item/quantidade")
                .param("usuarioEmail", "cliente@email.com")
                .param("itemId", "1")
                .param("novaQuantidade", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(3));
    }

    @Test
    void excluirItem_DeveRetornarNoContent() throws Exception {
        Mockito.doNothing().when(vendaService).excluirItem(any());

        mockMvc.perform(delete("/vendas/item/excluir")
                .param("usuarioEmail", "cliente@email.com")
                .param("itemId", "1"))
                .andExpect(status().isNoContent());
    }
}
