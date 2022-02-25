package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
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

    public  List<Resumo>  getResumoByProduto(Produto produto) { return resumoRepository.findByProduto(produto);
    }

    public List<Resumo> getResumoByCliente(Cliente cliente) {
        List<Resumo> resumos = resumoRepository.findByCliente(cliente);
        if(resumos.isEmpty()){
            throw new RuntimeException("Cliente não possui produtos no carrinho.");
        }
        return resumos;
    }

    public List<Resumo> listarResumos() {
        return resumoRepository.findAll();
    }

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

    public void removerResumo(Resumo resumo) { resumoRepository.delete(resumo);
    }

    public Resumo criaResumo(int numItens, Produto produto, Cliente cliente) {
        Resumo resumo = new Resumo(produto, numItens, cliente);
        return resumo;
    }

    @Override
    public List<Resumo> getResumoByProdutoAndCliente(Produto produto, Cliente cliente) {
        return resumoRepository.findByProdutoAndCliente(produto, cliente);
    }

    @Override
    public List<Resumo> getResumosNaoComprados(List<Resumo> resumos) {
        List<Resumo> resumosNaoComprados = new ArrayList<>();

        resumos.forEach(resumo -> {
            if(!resumo.getComprado()){
                resumosNaoComprados.add(resumo);
            }
        });
        if(resumosNaoComprados.isEmpty()){
            throw new RuntimeException("Não possui produtos no carrinho.");
        }
        return resumosNaoComprados;
    }

    public Boolean verificaSeHáResumoNãoComprado(List<Resumo> resumos) {
        int i = 0;
        Boolean existeItemNãoComprado = false;
        while (i < resumos.size() && !existeItemNãoComprado){
            if(!resumos.get(i).getComprado()){
                existeItemNãoComprado = true;
            }
            i++;
        }
        return existeItemNãoComprado;
    }
}
