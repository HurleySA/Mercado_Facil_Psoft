package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaPadrão extends FormaEntrega{

    public FormaEntregaPadrão() {
        super("Padrão", 20);
    }
    @Override
    public String getTipoEntrega() {
        return "Padrão";
    }
    @Override
    public double getTotalEntrega() {
        return 20;
    }
}
