package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaFragil extends FormaEntrega{

    public FormaEntregaFragil() {
        super("Fragil", 80);
    }
    @Override
    public String getTipoEntrega() {
        return "Fragil";
    }
    @Override
    public double getTotalEntrega() {
        return 80;
    }
}
