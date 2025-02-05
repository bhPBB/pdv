package com.brunobomfim.pdv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.brunobomfim.pdv.models.Item;
import com.brunobomfim.pdv.models.ItemPk;
import com.brunobomfim.pdv.models.Usuario;
import com.brunobomfim.pdv.repositories.ItemRepository;
import com.brunobomfim.pdv.repositories.UsuarioRepository;
import com.brunobomfim.pdv.services.ItemService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private ItemPk itemPk;
    private Item item;

    @BeforeEach
    void setUp() {
        // Limpar o banco antes de cada teste
        itemRepository.deleteAll();
        usuarioRepository.deleteAll();

        // Criando e salvando o usuário
        usuario = new Usuario();
        usuario.setEmail("user@email.com");
        usuario.setNome("user");
        usuario.setSenha("senha");
        usuarioRepository.save(usuario);

        // Criando o Item
        itemPk = new ItemPk(usuario.getEmail(), 1L);
        item = new Item(itemPk, "Produto A", 10.0, 5, usuario);
        itemService.cadastrarItem(item);
    }

    @Test
    void deveCadastrarItem() {
        // Recuperando o item do banco
        Item i = itemRepository.findById(itemPk).orElse(null);
        assertNotNull(i);
        assertEquals("Produto A", i.getNome());
    }

    @Test
    void deveRecuperarTodosItens() {
        ItemPk itemPk2 = new ItemPk(usuario.getEmail(), 2L);
        Item item2 = new Item(itemPk2, "Produto B", 15.0, 10, usuario);

        // Salvando os itens no banco de dados H2
        itemRepository.save(item2);

        // Chamando o método a ser testado
        List<Item> itensRecuperados = itemService.recuperarTodosItens();

        // Verificando o resultado
        assertNotNull(itensRecuperados);
        assertEquals(2, itensRecuperados.size());
        assertEquals("Produto A", itensRecuperados.get(0).getNome());
        assertEquals("Produto B", itensRecuperados.get(1).getNome());
    }

    @Test
    void deveExcluirItem() {
        itemService.excluirItem(itemPk);

        // Verificando se o item foi excluído
        assertTrue(itemRepository.findById(itemPk).isEmpty());
    }

    @Test
    void deveAtualizarPrecoItem() {
        Item itemAtualizado = itemService.atualizarPreco(itemPk, 15.0);

        assertEquals(15.0, itemAtualizado.getPreco());
    }

    @Test
    void deveAtualizarQuantidadeItem() {
        Item itemAtualizado = itemService.atualizarQuantidade(itemPk, 10);

        assertEquals(10, itemAtualizado.getQuantidade());
    }
}
