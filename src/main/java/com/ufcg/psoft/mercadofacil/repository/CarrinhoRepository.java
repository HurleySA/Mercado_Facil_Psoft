package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{
    List<Resumo> findByCliente(Cliente cliente);
}