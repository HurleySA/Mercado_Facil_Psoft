package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;

@Entity
public class ClienteEspecial extends Cliente {

    public ClienteEspecial(Long cpf, String nome, Integer idade, String endereco, String perfil) {
        super(cpf, nome, idade, endereco, perfil);;
    }
    public ClienteEspecial() {
        super();
    }

    @Override
    public String toString(){
        return "Especial";
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        if(quantidade > 10){
            return valor * 0.9;
        }
        return valor;
    }
}
