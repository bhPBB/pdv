package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPk implements Serializable {

    private String usuarioEmail;
    private Long codigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPk itemPk = (ItemPk) o;
        return Objects.equals(usuarioEmail, itemPk.usuarioEmail) && Objects.equals(codigo, itemPk.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioEmail, codigo);
    }
}