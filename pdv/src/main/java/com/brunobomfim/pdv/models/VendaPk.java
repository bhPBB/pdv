package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VendaPk implements Serializable {
    
    private String usuarioEmail;    
    private Long codigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaPk vendaPk = (VendaPk) o;
        return Objects.equals(usuarioEmail, vendaPk.usuarioEmail) && Objects.equals(codigo, vendaPk.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioEmail, codigo);
    }
}
