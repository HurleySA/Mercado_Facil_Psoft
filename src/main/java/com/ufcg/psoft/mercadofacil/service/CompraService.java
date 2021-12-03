package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraService {
    public List<Compra> listarCompras();

    public Optional<Compra> getCompraById(long id);

    public List<Compra> getComprasByCliente(Cliente cliente);

    public Compra criaCompra(CompraDTO compraDTO);

    public void salvarCompra(Compra compra);

    public void removerCompra(Compra compra);
}

