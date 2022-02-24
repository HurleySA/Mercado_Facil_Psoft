package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class Calculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getEstrategiaName() {
        return estrategiaName;
    }

    public void setEstrategiaName(String estrategiaName) {
        this.estrategiaName = estrategiaName;
    }

    private String estrategiaName;

    public Calculo(){}

    public Calculo(String estrategiaName){
        this.estrategiaName = estrategiaName;
    }

    public abstract  double calcular();

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
