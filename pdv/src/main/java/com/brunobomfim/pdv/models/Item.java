package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "itens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @EmbeddedId
    private ItemPk id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @MapsId("usuarioEmail")
    private Usuario usuario;

    public Item(Usuario u, String nome, long cod, double preco, int qtd) {
        id = new ItemPk(u.getEmail(), cod);
        this.nome = nome;
        this.preco = preco;
        quantidade = qtd;
        usuario = u;
    }
}


