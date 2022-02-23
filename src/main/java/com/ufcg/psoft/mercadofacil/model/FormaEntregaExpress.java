package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaExpress extends FormaEntrega{

    public FormaEntregaExpress() {
        super("Express", 50);
    }
    @Override
    public String getTipoEntrega() {
        return "Express";
    }
    @Override
    public double getTotalEntrega() {
        return 50;
    }
}
