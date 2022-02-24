package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;

@Entity
public class CalculoFragil extends Calculo {
    public CalculoFragil(){
        super("Fragil");
    }
    @Override
    public double calcular() {
        return 1.3;
    }
}
