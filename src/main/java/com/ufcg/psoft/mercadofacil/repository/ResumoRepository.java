package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Resumo;

import java.util.List;

public interface ResumoRepository extends JpaRepository<Resumo, Long>{
    Resumo findByProduto(Produto produto);
}