package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompraService {
    public List<Compra> listarCompras();

    public Compra getCompraById(long id);

    public List<Compra> getComprasByCliente(Cliente cliente);

    public Compra criaCompra(List<Resumo> resumos, int quantidadeProdutos, String data, String formaPagamento, Cliente cliente);

    public void salvarCompra(Compra compra);

    public void removerCompra(Compra compra);

    ResponseEntity<?> criaCompraById(long idCliente, String formaPagamento);

    ResponseEntity<?> listarComprasResponse(Long idCliente);

    ResponseEntity<?> listarComprasByIds(long idCliente, long idCompra);
}

