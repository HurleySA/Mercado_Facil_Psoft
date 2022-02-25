package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaExpress extends FormaEntrega{

    public FormaEntregaExpress() {
        super("Express", 50);
    }

    public FormaEntregaExpress(Calculo calculo) {
        super("Express", 50, calculo);
    }

    @Override
    public double calcular() {
        return 50 * getEstrategia().calcular();
    }

}
