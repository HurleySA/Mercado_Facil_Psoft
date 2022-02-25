package com.ufcg.psoft.mercadofacil.model.FormaEntrega;

import com.ufcg.psoft.mercadofacil.model.Calculo.Calculo;
import com.ufcg.psoft.mercadofacil.model.Calculo.CalculoComum;
import com.ufcg.psoft.mercadofacil.model.Calculo.CalculoFragil;
import com.ufcg.psoft.mercadofacil.model.Calculo.CalculoRefrigeracao;
import com.ufcg.psoft.mercadofacil.model.Resumo;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class FormaEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Calculo estrategia;


    private String tipoEntrega;

    private double totalEntrega;

    public FormaEntrega() {}

    public FormaEntrega(String tipoEntrega, double totalEntrega) {
        this.estrategia = new CalculoComum();
        this.tipoEntrega = tipoEntrega;
        this.totalEntrega = totalEntrega;
    }

    public FormaEntrega(String tipoEntrega, double totalEntrega, Calculo estrategia) {
        this.estrategia = estrategia;
        this.tipoEntrega = tipoEntrega;
        this.totalEntrega = totalEntrega;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public double getTotalEntrega() {
        return totalEntrega;
    }

    public void setTotalEntrega(double totalEntrega) {
        this.totalEntrega = totalEntrega;
    }

    public Calculo getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(Calculo estrategia) {
        this.estrategia = estrategia;
    }

    public void modificaEstrategia(List<Resumo> resumos){
        boolean existRefrigeracao = resumos.stream().anyMatch(res ->  res.getProduto().getCategoria().equals("REFRIGERACAO"));
        boolean existFragil = resumos.stream().anyMatch(res ->  res.getProduto().getCategoria().equals("FRAGIL"));
        if(existRefrigeracao && !existFragil) setEstrategia(new CalculoRefrigeracao());
        if(existFragil && !existRefrigeracao) setEstrategia(new CalculoFragil());
    }

    public abstract double calcular();
}
