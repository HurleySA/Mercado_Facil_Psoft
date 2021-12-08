package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CompraService {
    public List<Compra> listarCompras();

    public Optional<Compra> getCompraById(long id);

    public List<Compra> getComprasByCliente(Cliente cliente);

    public Compra criaCompra(List<Resumo> resumos, int quantidadeProdutos, String data, Cliente cliente);

    public void salvarCompra(Compra compra);

    public void removerCompra(Compra compra);

    ResponseEntity<?> criaCompraById(long idCliente);

    ResponseEntity<?> listarComprasResponse(Long idCliente);

    ResponseEntity<?> listarComprasByIds(long idCliente, long idCompra);
}

