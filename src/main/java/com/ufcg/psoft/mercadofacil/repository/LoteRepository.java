package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Lote;

import java.util.List;
import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Long>{
    List<Lote> findByProduto(Produto produto);
}