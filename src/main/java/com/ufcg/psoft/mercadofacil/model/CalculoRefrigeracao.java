package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;

@Entity
public class CalculoRefrigeracao extends Calculo{
    @Override
    public double calcular() {
        return 1.5;
    }
}
