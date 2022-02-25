package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;

public class ResumoDTO {

    private Produto produto;

    private int quantidade;

    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
