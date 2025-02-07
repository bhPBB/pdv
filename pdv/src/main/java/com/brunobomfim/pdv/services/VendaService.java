package com.brunobomfim.pdv.services;

import com.brunobomfim.pdv.models.*;
import com.brunobomfim.pdv.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vr;
    
    @Autowired
    private VendaItemRepository vir;

    @Autowired
    private ItemRepository ir;

    @Autowired
    private UsuarioRepository ur;

    private Usuario checarUsuario(String email) {
        return ur.findById(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private Item checarItem(ItemPk pk) {
        return ir.findById(pk)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    @Transactional
    public void excluirItem(ItemPk pk) {
        ir.deleteById(pk);
    }
    
    @Transactional
    public Venda registrarVenda(String usuarioEmail, Map<Item, Integer> itens) {
        
        // Obter o próximo código para a venda
        var cod = vr.getNextVendaCodigo(usuarioEmail);

        var u = checarUsuario(usuarioEmail);
        var v = new Venda(u, cod, itens);
        vr.save(v);
        
        var vItens = v.getVendaItens();
        vir.saveAll(vItens);
        
        for (VendaItem vi : vItens) {
            
            var item = vi.getItem();
            
            atualizarQuantidadeItem(
                item.getId(),
                (item.getQuantidade() - vi.getQuantidade())
            );
        }
        return v;
    }

    @Transactional
    public Item registrarItem(String usuarioEmail, String nome, long cod , double preco, int qtd) {
       
        var u = checarUsuario(usuarioEmail);
        return ir.save(new Item(u, nome, cod, preco, qtd));
    }

    @Transactional
    public Item atualizarPrecoItem(ItemPk pk, double preco) {
        var item = checarItem(pk);
        item.setPreco(preco);
        return ir.save(item);
    }
    
    @Transactional
    public Item atualizarQuantidadeItem(ItemPk pk, int qtd) {
        var item = checarItem(pk);
        item.setQuantidade(qtd);
        return ir.save(item);
    }

    public List<Item> recuperarTodosItens(String usuarioEmail) {
        return ir.findByIdUsuarioEmail(usuarioEmail);
    }
}
