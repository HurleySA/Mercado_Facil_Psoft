package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Resumo> resumosPedidos = new ArrayList<>();

    @OneToOne
    private Cliente cliente;

    public Carrinho(){

    }

    public Carrinho(List<Resumo> resumosPedidos, Cliente cliente) {
        this.resumosPedidos = resumosPedidos;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }
    public List<Resumo> getResumosPedidos() {
        return resumosPedidos;
    }

    public void setResumosPedidos(List<Resumo> resumosPedidos) {
        this.resumosPedidos = resumosPedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "resumosPedidos=" + resumosPedidos +
                ", cliente=" + cliente +
                '}';
    }
}
