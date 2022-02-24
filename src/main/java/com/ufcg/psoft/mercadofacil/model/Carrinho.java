package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade=CascadeType.PERSIST)
    private FormaEntrega formaEntrega;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Resumo> resumosPedidos;

    @OneToOne(mappedBy = "carrinho")
    private Cliente cliente;

    public Carrinho(FormaEntrega formaEntrega){
        this.resumosPedidos = new ArrayList<>();
        this.formaEntrega = formaEntrega;
    }

    public Carrinho(List<Resumo> resumosPedidos, FormaEntrega formaEntrega) {
        this.resumosPedidos = new ArrayList<>(resumosPedidos);
        this.formaEntrega = formaEntrega;
    }

    public Carrinho() {
        this.resumosPedidos = new ArrayList<>();
        this.formaEntrega = new FormaEntregaRetirada();
    }

    public Long getId() {
        return id;
    }

    public FormaEntrega getFormaEntrega() {
        return formaEntrega;
    }

    public void setFormaEntrega(FormaEntrega formaEntrega) {
        this.formaEntrega = formaEntrega;
    }

    public List<Resumo> getResumosPedidos() {
        return resumosPedidos;
    }

    public void adicionaResumo(Resumo resumo) { resumosPedidos.add(resumo);}

    public void removeResumo(Resumo resumo) { resumosPedidos.remove(resumo);}

    public double calculaEntrega(){
        return formaEntrega.calcular();
    }

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
