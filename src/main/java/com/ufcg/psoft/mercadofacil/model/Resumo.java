package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
public class Resumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Produto produto;
    private int quantidade;

    @ManyToOne
    private Cliente cliente;

    public Resumo() {
    }

    public Resumo(Produto produto, int quantidade, Cliente cliente) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Resumo{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}
