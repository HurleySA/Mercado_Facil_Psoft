package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class Calculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract  double calcular();

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
