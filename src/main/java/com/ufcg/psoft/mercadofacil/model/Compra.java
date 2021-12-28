package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Resumo> resumos;

    private int quantidadeProdutos;
    private String data;
    private String formaPagamento;
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    public Compra() {
        this.resumos = new ArrayList<>();
    }

    public Compra(Cliente cliente, String data, String formaPagamento) {
        this.resumos = new ArrayList<>(cliente.getCarrinho().getResumosPedidos());
        this.quantidadeProdutos = resumos.size();
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.total = getTotalComprado(resumos, formaPagamento);
    }

    public Compra(List<Resumo> resumos, int quantidadeProdutos, String data, String formaPagamento, Cliente cliente) {
        this.resumos = new ArrayList<>(resumos);
        this.quantidadeProdutos = quantidadeProdutos;
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.total = getTotalComprado(resumos, formaPagamento);
    }

    protected BigDecimal getTotalComprado(List<Resumo> resumos, String formaPagamento){
        AtomicReference<BigDecimal> total = new AtomicReference<>(new BigDecimal(0));
        resumos.forEach(resumo -> {
            total.getAndSet(new BigDecimal(resumo.getQuantidade()).multiply(resumo.getProduto().getPreco()).add(total.get()));
        } );
        if(Objects.equals(formaPagamento, "Paypal")) total.set(total.get().multiply(BigDecimal.valueOf(1.2)));
        if (Objects.equals(formaPagamento, "Cartão de Crédito")) total.set(total.get().multiply(BigDecimal.valueOf(1.5)));
        return total.get();

    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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