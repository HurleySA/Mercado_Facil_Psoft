package com.ufcg.psoft.mercadofacil.model.FormaEntrega;


import com.ufcg.psoft.mercadofacil.model.Calculo.Calculo;

import javax.persistence.Entity;

@Entity
public class FormaEntregaPadrão extends FormaEntrega{

    public FormaEntregaPadrão() {
        super("Padrão", 20);
    }

    public FormaEntregaPadrão(Calculo calculo) {
        super("Padrão", 20, calculo);
    }

    @Override
    public double calcular() {
        return 20 * getEstrategia().calcular();
    }

}
