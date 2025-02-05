package com.brunobomfim.pdv.services;

import com.brunobomfim.pdv.models.Item;
import com.brunobomfim.pdv.models.ItemPk;
import com.brunobomfim.pdv.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository ir;

    public Item cadastrarItem(Item item) {
        return ir.save(item);
    }

    public void excluirItem(ItemPk itemPk) {
        ir.deleteById(itemPk);
    }

    public Item atualizarPreco(ItemPk itemPk, double novoPreco) {
        Item item = ir.findById(itemPk)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setPreco(novoPreco);
        return ir.save(item);
    }

    public Item atualizarQuantidade(ItemPk itemPk, int novaQuantidade) {
        Item item = ir.findById(itemPk)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setQuantidade(novaQuantidade);
        return ir.save(item);
    }

    public List<Item> recuperarTodosItens() {
        return ir.findAll();
    }
}
