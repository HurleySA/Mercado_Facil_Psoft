package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Produto> produtos;

    private int quantidadeProdutos;
    private String data;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    public Compra() {
    }

    public Compra(Cliente cliente) {
        this.produtos = new ArrayList<>();
        this.quantidadeProdutos = produtos.size();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.data = formatter.format(date);
        this.cliente = cliente;
    }

    public Compra(List<Produto> produtos, int quantidadeProdutos, String data, Cliente cliente) {
        this.produtos = produtos;
        this.quantidadeProdutos = quantidadeProdutos;
        this.data = data;
        this.cliente = cliente;

    }


    public Long getId() {
        return id;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public int getQuantidade() {
        return quantidadeProdutos;
    }

    public void setQuantidade(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionaProduto(Produto produto) { produtos.add(produto);}

    public void removeProduto(Produto produto) { produtos.remove(produto);}
}