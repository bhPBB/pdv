package com.brunobomfim.pdv.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class VendaItemPk implements Serializable {

    private String vendaUsuario;
    private Long vendanNumero;
    private String itemUsuario;
    private Long itemCod;
}