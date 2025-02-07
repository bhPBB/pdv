package com.brunobomfim.pdv;

import com.brunobomfim.pdv.models.*;
import com.brunobomfim.pdv.repositories.*;
import com.brunobomfim.pdv.services.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class VendaServiceTest {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaItemRepository vendaItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String usuarioEmail = "cliente@email.com";
    private Usuario usuario;
    private Item item;
    
    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        itemRepository.deleteAll();
        vendaRepository.deleteAll();
        vendaItemRepository.deleteAll();

        usuario = new Usuario(usuarioEmail, "Cliente Teste", "senha123");
        usuarioRepository.save(usuario);

        item = new Item(usuario, "Produto 1", 1L, 10.0, 5);
        itemRepository.save(item);
    }

    @Test
    void registrarItem_DeveSalvarItem() {
        Item novoItem = vendaService.registrarItem(usuarioEmail, "Produto 2", 2L, 20.0, 10);
        assertNotNull(novoItem.getId());
        assertEquals("Produto 2", novoItem.getNome());
    }

    @Test
    void atualizarPrecoItem_DeveAlterarPreco() {
        Item atualizado = vendaService.atualizarPrecoItem(item.getId(), 15.0);
        assertEquals(15.0, atualizado.getPreco());
    }

    @Test
    void atualizarQuantidadeItem_DeveAlterarQuantidade() {
        Item atualizado = vendaService.atualizarQuantidadeItem(item.getId(), 8);
        assertEquals(8, atualizado.getQuantidade());
    }

    @Test
    void registrarVenda_DeveSalvarVenda() {
        Map<Item, Integer> itens = Map.of(item, 2);
        Venda venda = vendaService.registrarVenda(usuarioEmail, itens);
        assertNotNull(venda.getId());
        assertEquals(1, venda.getVendaItens().size());
        assertEquals(20.0, venda.getTotal());
    }

    @Test
    void excluirItem_DeveRemoverItem() {
        vendaService.excluirItem(item.getId());
        assertFalse(itemRepository.existsById(item.getId()));
    }
}
