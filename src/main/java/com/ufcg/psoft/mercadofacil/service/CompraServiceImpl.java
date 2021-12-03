package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompraServiceImpl implements CompraService{
    @Autowired
    private CompraRepository compraRepository;


    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> getCompraById(long id) { return compraRepository.findById(id);
    }
    public List<Compra> getComprasByCliente(Cliente cliente) {return compraRepository.findByCliente(cliente);}

    public Compra criaCompra(CompraDTO compraDTO){
        Compra compra = new Compra(compraDTO.getProdutos(), compraDTO.getQuantidadeProdutos(), compraDTO.getData(), compraDTO.getCliente() );
        return compra;
    }

    public void salvarCompra(Compra compra){ compraRepository.save(compra);}

    public void removerCompra(Compra compra){ compraRepository.delete(compra);}

}