package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Resumo> resumosPedidos = new ArrayList<>();

    public Carrinho(){
        this.resumosPedidos = new ArrayList<>();
    }

    public Carrinho(List<Resumo> resumosPedidos) {
        this.resumosPedidos = resumosPedidos;
    }

    public Long getId() {
        return id;
    }

    public List<Resumo> getResumosPedidos() {
        return resumosPedidos;
    }

    public void adicionaResumo(Resumo resumo) { resumosPedidos.add(resumo);}

    public void removeResumo(Resumo resumo) { resumosPedidos.remove(resumo);}


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