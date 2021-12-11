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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Resumo> resumos;

    private int quantidadeProdutos;
    private String data;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    public Compra() {
        this.resumos = new ArrayList<>();
    }

    public Compra(Cliente cliente, String data) {
        this.resumos = new ArrayList<>(cliente.getCarrinho().getResumosPedidos());
        this.quantidadeProdutos = resumos.size();
        this.data = data;
        this.cliente = cliente;
    }

    public Compra(List<Resumo> resumos, int quantidadeProdutos, String data, Cliente cliente) {
        this.resumos = new ArrayList<>(resumos);
        this.quantidadeProdutos = quantidadeProdutos;
        this.data = data;
        this.cliente = cliente;

    }


    public Long getId() {
        return id;
    }


    public List<Resumo> getResumos() {
        return resumos;
    }

    public void setResumos(List<Resumo> resumos) {
        this.resumos = new ArrayList<>(resumos);
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

    public void adicionaResumo(Resumo resumo) { resumos.add(resumo);}

    public void removeResumo(Resumo resumo) { resumos.remove(resumo);}

}