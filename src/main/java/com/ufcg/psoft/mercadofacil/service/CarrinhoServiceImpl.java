package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.repository.ResumoRepository;

import java.util.List;

public class CarrinhoServiceImpl implements CarrinhoService {

    private ResumoServiceImpl resumoService;

    @Override
    public List<Resumo> listarCarrinho() {
        return resumoService.listarResumos();
    }

    @Override
    public void adicionarResumo(Resumo resumo) {
         resumoService.salvarResumo(resumo);
    }

    @Override
    public void removerResumoCadastrado(Resumo resumo) {
        resumoService.removerResumo(resumo);
    }
}
