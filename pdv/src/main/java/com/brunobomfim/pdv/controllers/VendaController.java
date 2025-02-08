package com.brunobomfim.pdv.controllers;

import com.brunobomfim.pdv.models.*;
import com.brunobomfim.pdv.services.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    /**
     * Registra uma nova venda para um usuário.
     */
    @PostMapping("/registrar")
    public ResponseEntity<Venda> registrarVenda(
            @RequestParam String usuarioEmail,
            @RequestBody Map<Item, Integer> itens) {

        Venda venda = vendaService.registrarVenda(usuarioEmail, itens);
        return ResponseEntity.ok(venda);
    }

    /**
     * Obtém todos os itens de um usuário específico.
     */
    @GetMapping("/itens/{usuarioEmail}")
    public ResponseEntity<List<Item>> recuperarTodosItens(@PathVariable String usuarioEmail) {
        List<Item> itens = vendaService.recuperarTodosItens(usuarioEmail);
        return ResponseEntity.ok(itens);
    }

    /**
     * Atualiza o preço de um item.
     */
    @PutMapping("/item/preco")
    public ResponseEntity<Item> atualizarPrecoItem(
            @RequestParam String usuarioEmail,
            @RequestParam Long itemId,
            @RequestParam double novoPreco) {

        ItemPk pk = new ItemPk(usuarioEmail, itemId);
        Item itemAtualizado = vendaService.atualizarPrecoItem(pk, novoPreco);
        return ResponseEntity.ok(itemAtualizado);
    }

    /**
     * Atualiza a quantidade de um item.
     */
    @PutMapping("/item/quantidade")
    public ResponseEntity<Item> atualizarQuantidadeItem(
            @RequestParam String usuarioEmail,
            @RequestParam Long itemId,
            @RequestParam int novaQuantidade) {

        ItemPk pk = new ItemPk(usuarioEmail, itemId);
        Item itemAtualizado = vendaService.atualizarQuantidadeItem(pk, novaQuantidade);
        return ResponseEntity.ok(itemAtualizado);
    }

    /**
     * Exclui um item do sistema.
     */
    @DeleteMapping("/item/excluir")
    public ResponseEntity<Void> excluirItem(
            @RequestParam String usuarioEmail,
            @RequestParam Long itemId) {

        ItemPk pk = new ItemPk(usuarioEmail, itemId);
        vendaService.excluirItem(pk);
        return ResponseEntity.noContent().build();
    }
}
