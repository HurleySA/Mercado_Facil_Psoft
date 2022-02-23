package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class FormaEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoEntrega;

    private double totalEntrega;

    public FormaEntrega() {}

    public FormaEntrega(String tipoEntrega, double totalEntrega) {
        this.tipoEntrega = tipoEntrega;
        this.totalEntrega = totalEntrega;
    }


    public  abstract  String getTipoEntrega();
    public abstract double getTotalEntrega();
}
