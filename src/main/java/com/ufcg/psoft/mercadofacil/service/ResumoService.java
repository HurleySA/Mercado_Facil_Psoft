package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.http.ResponseEntity;

public interface ResumoService {

    public Optional<Resumo> getResumoById(long id);

    public List<Resumo> getResumoByProduto(Produto produto);

    public List<Resumo> getResumoByCliente(Cliente cliente);

    public List<Resumo> listarResumos();

    public ResponseEntity<?> listarResumosResponse();

    public void salvarResumo(Resumo resumo);

    public void removerResumo(Resumo resumo);

    public Resumo criaResumo(int numItens, Produto produto, Cliente cliente);


    List<Resumo> getResumoByProdutoAndCliente(Produto produto, Cliente cliente);

    List<Resumo> getResumosNaoComprados(List<Resumo> resumos);

    public Boolean verificaSeHáResumoNãoComprado(List<Resumo> resumoByProdutoAndCliente);
}
