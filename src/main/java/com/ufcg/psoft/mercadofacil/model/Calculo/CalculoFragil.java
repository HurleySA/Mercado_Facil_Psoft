package com.ufcg.psoft.mercadofacil.model.Calculo;

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
