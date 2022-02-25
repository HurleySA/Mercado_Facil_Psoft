package com.ufcg.psoft.mercadofacil.model.FormaEntrega;


import com.ufcg.psoft.mercadofacil.model.Calculo.Calculo;

import javax.persistence.Entity;

@Entity
public class FormaEntregaRetirada extends FormaEntrega{

    public FormaEntregaRetirada() {
        super("Retirada", 0);
    }

    public FormaEntregaRetirada(Calculo calculo) {
        super("Retirada", 0, calculo);
    }

    @Override
    public double calcular() {
        return 0 * getEstrategia().calcular();
    }


}
