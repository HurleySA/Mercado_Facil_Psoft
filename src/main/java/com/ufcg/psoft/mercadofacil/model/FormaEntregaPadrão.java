package com.ufcg.psoft.mercadofacil.model;


import javax.persistence.Entity;

@Entity
public class FormaEntregaPadrão extends FormaEntrega{

    public FormaEntregaPadrão() {
        super("Padrão", 20);
    }

    @Override
    public double calcular() {
        return 20 * getEstrategia().calcular();
    }

}
