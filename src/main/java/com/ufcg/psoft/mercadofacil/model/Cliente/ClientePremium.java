package com.ufcg.psoft.mercadofacil.model.Cliente;

import javax.persistence.Entity;

@Entity
public class ClientePremium extends Cliente {

    public ClientePremium(Long cpf, String nome, Integer idade, String endereco, String perfil) {
        super(cpf, nome, idade, endereco, perfil);
    }
    public ClientePremium() {
        super();
    }
    @Override
    public String toString(){
        return "Premium";
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        if(quantidade > 5){
            return valor * 0.9;
        }
        return valor;
    }
}
