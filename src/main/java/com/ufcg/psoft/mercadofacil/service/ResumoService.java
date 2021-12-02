package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.model.Produto;

public interface ResumoService {

    public Optional<Resumo> getResumoById(long id);

    public Optional<Resumo> getResumoByProduto(Produto produto);

    public List<Resumo> listarResumos();

    public void salvarResumo(Resumo resumo);

    public void removerResumo(Resumo resumo);

    public Resumo criaResumo(int numItens, Produto produto);


}
