package com.ufcg.psoft.mercadofacil.model.Calculo;

import javax.persistence.Entity;

@Entity
public class CalculoComum extends Calculo {

    public CalculoComum(){
        super("Comum");
    }
    @Override
    public double calcular() {
        return 1.1;
    }
}
