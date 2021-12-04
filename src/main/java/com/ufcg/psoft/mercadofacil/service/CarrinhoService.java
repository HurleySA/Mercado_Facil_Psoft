package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;


public interface CarrinhoService {

    public List<Resumo> listarCarrinho();

    List<Resumo> listarCarrinhoCliente(Cliente cliente);

    public void adicionarResumo(Resumo resumo);

    public void removerResumoCadastrado(Resumo resumo);

    public int getTotalProduto(Cliente cliente, Produto produto);


}
