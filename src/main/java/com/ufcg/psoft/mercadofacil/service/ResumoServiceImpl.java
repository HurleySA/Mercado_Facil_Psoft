package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.util.ErroLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<?> listarResumosResponse() {
        List<Resumo> resumos = this.listarResumos();

        if (resumos.isEmpty()) {
            return ErroLote.erroSemLotesCadastrados();
        }

        return new ResponseEntity<List<Resumo>>(resumos, HttpStatus.OK);
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
