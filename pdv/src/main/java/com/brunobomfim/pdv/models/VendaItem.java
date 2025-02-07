package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaItem {

    @EmbeddedId
    private VendaItemPk id;

    @ManyToOne
    @MapsId("vendaId") 
    @JoinColumns({
        @JoinColumn(name = "venda_usuario_email", referencedColumnName = "usuario_email"),
        @JoinColumn(name = "venda_codigo", referencedColumnName = "codigo")
    })
    private Venda venda;
    

    @ManyToOne
    @MapsId("itemId")
    @JoinColumns({
        @JoinColumn(name = "item_usuario_email", referencedColumnName = "usuario_email"),
        @JoinColumn(name = "item_codigo", referencedColumnName = "codigo")
    })
    private Item item;

    private int quantidade;

    public VendaItem(Venda venda, Item item, int quantidade) {
        
        this.venda = venda;
        this.item = item;

        var vPk = venda.getId();
        var iPk = item.getId();
        
        this.id = new VendaItemPk(
            vPk.getUsuarioEmail(),
            vPk.getCodigo(),
            iPk.getUsuarioEmail(),
            iPk.getCodigo()
        );
        
        this.quantidade = quantidade;
    }
}

