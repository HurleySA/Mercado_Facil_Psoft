package com.ufcg.psoft.mercadofacil.model.Cliente;

import javax.persistence.Entity;

@Entity
public class ClienteNormal extends Cliente {

    public ClienteNormal(Long cpf, String nome, Integer idade, String endereco, String perfil) {
        super(cpf, nome, idade, endereco, perfil);
    }
    public ClienteNormal() {
        super();
    }

    @Override
    public String toString(){
        return "Normal";
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        return valor;
    }
}
