package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formaEntrega;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Resumo> resumosPedidos;

    @OneToOne(mappedBy = "carrinho")
    private Cliente cliente;

    public Carrinho(String formaEntrega){
        this.resumosPedidos = new ArrayList<>();
        this.formaEntrega = formaEntrega;
    }

    public Carrinho(List<Resumo> resumosPedidos, String formaEntrega) {
        this.resumosPedidos = new ArrayList<>(resumosPedidos);
        this.formaEntrega = formaEntrega;
    }

    public Carrinho() {

    }

    public Long getId() {
        return id;
    }

    public String getFormaEntrega() {
        return formaEntrega;
    }

    public void setFormaEntrega(String formaEntrega) {
        this.formaEntrega = formaEntrega;
    }

    public List<Resumo> getResumosPedidos() {
        return resumosPedidos;
    }

    public void adicionaResumo(Resumo resumo) { resumosPedidos.add(resumo);}

    public void removeResumo(Resumo resumo) { resumosPedidos.remove(resumo);}

    public void limpaCarrinho() {resumosPedidos = new ArrayList<>();}
    public void setResumosPedidos(List<Resumo> resumosPedidos) {
        this.resumosPedidos = resumosPedidos;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "resumosPedidos=" + resumosPedidos +
                '}';
    }


}
