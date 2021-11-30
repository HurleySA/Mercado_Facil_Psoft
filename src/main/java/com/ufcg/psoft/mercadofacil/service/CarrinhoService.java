package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Resumo;

public interface CarrinhoService {

    public List<Resumo> listarCarrinho();

    public void adicionarResumo(Resumo resumo);

    public void removerResumoCadastrado(Resumo resumo);

}
