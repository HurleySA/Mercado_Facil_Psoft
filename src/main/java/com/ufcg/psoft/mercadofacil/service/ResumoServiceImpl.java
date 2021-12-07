package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ResumoRepository;

import javax.persistence.EntityExistsException;

@Service
public class ResumoServiceImpl implements ResumoService {

    @Autowired
    private ResumoRepository resumoRepository;

    @Override
    public Resumo getResumoById(long id) {
        return resumoRepository.findById(id).orElseThrow(() -> new EntityExistsException("Resumo não encontrado."));
    }

    public  List<Resumo>  getResumoByProduto(Produto produto) { return resumoRepository.findByProduto(produto);
    }

    public List<Resumo> getResumoByCliente(Cliente cliente) {
        return resumoRepository.findByCliente(cliente);
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
    public Optional<Resumo> getResumoByProdutoAndCliente(Produto produto, Cliente cliente) {
        return resumoRepository.findByProdutoAndCliente(produto, cliente);
    }
}
