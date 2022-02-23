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
    private double totalPago;

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
        this.totalPago = getTotalComprado(resumos, formaPagamento, cliente.getPerfil());
    }

    public Compra(List<Resumo> resumos, int quantidadeProdutos, String data, String formaPagamento, Cliente cliente) {
        this.resumos = new ArrayList<>(resumos);
        this.quantidadeProdutos = quantidadeProdutos;
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.totalPago = getTotalComprado(resumos, formaPagamento, cliente.getPerfil());
    }

    protected double getTotalComprado(List<Resumo> resumos, String formaPagamento, String perfil){
        double total = getTotalInicial(resumos);
        int totalItem = getTotalItens(resumos);
        return getTotalDescontos(total, totalItem, formaPagamento, perfil);

    }

    protected  double getTotalFormaPagamento(double total, String formaPagamento){
        if(formaPagamento.equals("Paypal")){
            total *= 1.02;
        }
        if (formaPagamento.equals("Cartão de Crédito")) {
            total *= 1.05;
        };
        return total;
    }

    protected double getTotalDescontosPerfilItens(double total, int totalItem, String perfil){
        if(perfil.equals("Especial") && totalItem > 10 ){
            total *= 0.5;
        };
        if(perfil.equals("Premium") && totalItem > 5){
            total *= 0.5;
        };
        return total;
    }

    protected double getTotalDescontos(double total, int totalItem, String formaPagamento, String perfil){
        double totalFormaPagamento = getTotalFormaPagamento(total, formaPagamento);
        double totalDescontos = cliente.descontoCompras(totalFormaPagamento, totalItem);


        return totalDescontos;
    }


    protected double getTotalInicial(List<Resumo> resumos){
        int total= 0;
        for(int i = 0; i < resumos.size(); i++){
            total += resumos.get(i).getTotalComprado();
        }
        return total;
    }

    protected int getTotalItens(List<Resumo> resumos){
        int totalItem = 0;
        for(int i = 0; i < resumos.size(); i++){
            totalItem += resumos.get(i).getQuantidade();
        }
        return totalItem;
    }

    public double getTotal() {
        return totalPago;
    }

    public void setTotal(double total) {
        this.totalPago = total;
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