package com.ufcg.psoft.mercadofacil.model.Calculo;

import javax.persistence.Entity;

@Entity
public class CalculoRefrigeracao extends Calculo{
    public CalculoRefrigeracao(){
        super("Refrigeração");
    }
    @Override
    public double calcular() {
        return 1.5;
    }
}
