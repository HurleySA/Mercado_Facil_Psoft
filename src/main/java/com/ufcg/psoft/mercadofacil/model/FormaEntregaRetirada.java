package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaRetirada extends FormaEntrega{

    public FormaEntregaRetirada() {
        super("Retirada", 0);
    }

    @Override
    public double calcular() {
        return 0;
    }


}
