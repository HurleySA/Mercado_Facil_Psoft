package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.ResumoService;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraApiController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    CompraService compraService;

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    ResumoService resumoService;

    @RequestMapping(value="/compra/{idCliente}", method = RequestMethod.POST)
    public ResponseEntity<?> realizaCompra(@PathVariable("idCliente") long idCliente){
        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();
        List<Resumo> resumos = cliente.getCarrinho().getResumosPedidos();
        if(resumos.isEmpty()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO POSSUI PRODUTOS NO CARRINHO."), HttpStatus.CONFLICT);
        }
        Compra compra = compraService.criaCompra(resumos, resumos.size(), "04/12/2021", cliente);

        compraService.salvarCompra(compra);

        clienteService.limpaCarrinho(cliente);
        clienteService.salvarClienteCadastrado(cliente);


        return new ResponseEntity<Compra>(compra, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/compras/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> listaCompras(@PathVariable("idCliente") long idCliente){
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

    @RequestMapping(value = "/compras/{idCliente}/{idCompra}", method = RequestMethod.GET)
    public ResponseEntity<?> listaCompras(@PathVariable("idCliente") long idCliente, @PathVariable("idCompra") long idCompra){
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
