package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaRefrigeracao extends FormaEntrega{

    public FormaEntregaRefrigeracao() {
        super("Refrigeração", 100);
    }
    @Override
    public String getTipoEntrega() {
        return "Refrigeração";
    }
    @Override
    public double getTotalEntrega() {
        return 100;
    }
}
