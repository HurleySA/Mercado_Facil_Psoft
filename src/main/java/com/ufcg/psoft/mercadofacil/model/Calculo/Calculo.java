package com.ufcg.psoft.mercadofacil.model.Calculo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class Calculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estrategiaName;

    public Calculo(){}

    public Calculo(String estrategiaName){
        this.estrategiaName = estrategiaName;
    }

    public abstract  double calcular();

    public String getEstrategiaName() {
        return estrategiaName;
    }

    public void setEstrategiaName(String estrategiaName) {
        this.estrategiaName = estrategiaName;
    }
}
