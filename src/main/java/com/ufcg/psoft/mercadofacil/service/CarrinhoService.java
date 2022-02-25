package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.http.ResponseEntity;


public interface CarrinhoService {

    public List<Resumo> listarCarrinho();

    List<Resumo> listarCarrinhoCliente(Cliente cliente);

    public void adicionarResumo(Resumo resumo);

    public void removerResumoCadastrado(Resumo resumo);

    public int getTotalProduto(Cliente cliente, Produto produto);


    ResponseEntity<?> listaCarrinhoByClienteId(long idCliente);

    ResponseEntity<?> adicionarResumoByIds(long idCliente, long idProduto, int numItens, String formaEntrega);

    ResponseEntity<?> removerResumoCadastradoByIds(long idCliente, long idProduto);

    ResponseEntity<?> calculaEntrega(long idCliente);

    ResponseEntity<?> modificaFormaEntrega(long idCliente, String formaEntrega);
}
