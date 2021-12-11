package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Resumo;

import java.util.List;
import java.util.Optional;

public interface ResumoRepository extends JpaRepository<Resumo, Long>{
    List<Resumo> findByProduto(Produto produto);

    List<Resumo> findByProdutoAndCliente(Produto produto, Cliente cliente);

    List<Resumo> findByCliente(Cliente cliente);
}