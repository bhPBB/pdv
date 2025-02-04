package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPk implements Serializable {

    @Column(name = "usuario_email")
    private String usuarioEmail;

    @Column(name = "id")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemId itemId = (ItemId) o;
        return Objects.equals(usuarioEmail, itemId.usuarioEmail) && Objects.equals(id, itemPk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioEmail, id);
    }
}

