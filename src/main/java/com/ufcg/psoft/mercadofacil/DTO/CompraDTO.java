package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;

import java.util.List;


public class CompraDTO {

    private List<Resumo> resumos;

    private int quantidadeProdutos;

    private String data;

    private Cliente cliente;

    public List<Resumo> getResumos() {
        return resumos;
    }

    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public String getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
