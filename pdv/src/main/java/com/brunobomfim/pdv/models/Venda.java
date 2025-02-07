package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Venda {

    @EmbeddedId
    private VendaPk id;

    @ManyToOne
    @MapsId("usuarioEmail")
    @JoinColumn(name = "usuario_email", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaItem> vendaItens = new ArrayList<>();

    private double total;

    private long timestamp;

    public Venda(Usuario usuario, long cod, Map<Item, Integer> itens) {
        
        this.timestamp = System.currentTimeMillis();

        this.id = new VendaPk(usuario.getEmail(), cod);
        
        this.usuario = usuario;
        
        this.vendaItens = new ArrayList<VendaItem>(itens.entrySet().stream()
            .map(entry -> new VendaItem(this, entry.getKey(), entry.getValue()))
            .toList());
        
        this.total = vendaItens.stream()
            .mapToDouble(v -> v.getItem().getPreco() * v.getQuantidade())
            .sum();
    }
}
