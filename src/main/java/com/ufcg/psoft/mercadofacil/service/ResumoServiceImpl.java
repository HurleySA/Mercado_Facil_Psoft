package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ResumoRepository;

@Service
public class ResumoServiceImpl implements ResumoService {

    @Autowired
    private ResumoRepository resumoRepository;

    @Override
    public Optional<Resumo> getResumoById(long id) { return resumoRepository.findById(id);
    }

    public  Optional<Resumo>  getResumoByProduto(Produto produto) { return resumoRepository.findByProduto(produto);
    }

    public List<Resumo> listarResumos() {
        return resumoRepository.findAll();
    }

    public void salvarResumo(Resumo resumo) {
        resumoRepository.save(resumo);
    }

    @Override
    public void removerResumo(Resumo resumo) { resumoRepository.delete(resumo);
    }

    public Resumo criaResumo(int numItens, Produto produto) {
        Resumo resumo = new Resumo(produto, numItens);
        return resumo;
    }
}
