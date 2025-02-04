package com.brunobomfim.pdv.models;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.*;
import lombok.*;

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
    private float preco;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @MapsId("usuarioEmail")
    private Usuario usuario;
}