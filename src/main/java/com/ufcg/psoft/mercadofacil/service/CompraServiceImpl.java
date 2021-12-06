package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CompraServiceImpl implements CompraService{
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    CompraService compraService;

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    ResumoService resumoService;

    @Autowired
    LoteService loteService;

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> getCompraById(long id) { return compraRepository.findById(id);
    }
    public List<Compra> getComprasByCliente(Cliente cliente) {return compraRepository.findByCliente(cliente);}

    public Compra criaCompra(List<Resumo> resumos, int quantidadeProdutos, String data,Cliente cliente){
        Compra compra = new Compra(resumos, quantidadeProdutos, data,cliente);
        return compra;
    }

    public void salvarCompra(Compra compra){ compraRepository.save(compra);}

    public void removerCompra(Compra compra){ compraRepository.delete(compra);}

    @Override
    public ResponseEntity<?> criaCompraById(long idCliente) {

        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();

        List<Resumo> resumos = resumoService.getResumoByCliente(cliente);
       //List<Resumo> resumos = cliente.getCarrinho().getResumosPedidos();
        if(resumos.isEmpty()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO POSSUI PRODUTOS NO CARRINHO."), HttpStatus.CONFLICT);
        }

        resumos.forEach(resumo -> {
            if(resumo.getQuantidade() == loteService.getTotalByProduto(resumo.getProduto()).get()){
                loteService.removerLote(loteService.getLoteByProduto(resumo.getProduto()));
            }else{
                loteService.atualizaLote(loteService.getLoteByProduto(resumo.getProduto()), resumo.getQuantidade());
            }

        });
        Compra compra = compraService.criaCompra(resumos, resumos.size(), "04/12/2021", cliente);

        compraService.salvarCompra(compra);
        compra.setResumos(new ArrayList<>());
        clienteService.limpaCarrinho(cliente);
        clienteService.salvarClienteCadastrado(cliente);

        resumos.forEach(resumo -> {
            resumoService.removerResumo(resumo);
        });

        return new ResponseEntity<  List<Resumo>>(resumos, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> listarComprasResponse(Long idCliente) {
        Optional<Cliente> cliente = clienteService.getClienteById(idCliente);

        if (!cliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        List<Compra> compras = cliente.get().getCompras();

        if(compras.size() == 0){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO POSSUI COMPRAS."), HttpStatus.CONFLICT);

        }
        return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> listarComprasByIds(long idCliente, long idCompra) {
        Optional<Cliente> cliente = clienteService.getClienteById(idCliente);

        if (!cliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }

        Optional<Compra> compra = compraService.getCompraById(idCompra);

        if (!compra.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("COMPRA NÃO EXISTENTE."), HttpStatus.CONFLICT);
        }

        if(!cliente.get().getCompras().contains(compra.get())){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("COMPRA NÃO PERTENTE AO CLIENTE;"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Compra>(compra.get(), HttpStatus.OK);
    }

}
