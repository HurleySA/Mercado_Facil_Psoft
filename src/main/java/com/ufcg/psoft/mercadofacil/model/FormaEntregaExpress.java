package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaExpress extends FormaEntrega{

    public FormaEntregaExpress() {
        super("Express", 50);
    }

    @Override
    public double calcular() {
        return 50;
    }

}
