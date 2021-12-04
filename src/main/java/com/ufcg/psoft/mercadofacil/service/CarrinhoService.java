package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public interface CarrinhoService {

    public List<Resumo> listarCarrinho();

    List<Resumo> listarCarrinhoCliente(Cliente cliente);

    public void adicionarResumo(Resumo resumo);

    public void removerResumoCadastrado(Resumo resumo);


}
