package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.service.*;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {

    @Autowired
    CarrinhoService carrinhoService;


    @RequestMapping(value = "/carrinho/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> consultaCarrinho(@PathVariable("idCliente") long idCliente) {
        return carrinhoService.listaCarrinhoByClienteId(idCliente);
    }

    @RequestMapping(value = "/carrinho/entrega/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> consultaTotalEntrega(@PathVariable("idCliente") long idCliente) {
        return carrinhoService.calculaEntrega(idCliente);
    }

    @RequestMapping(value = "/carrinho/{idCliente}/{idProduto}/{formaEntrega}", method = RequestMethod.POST)
    public ResponseEntity<?> adicionaAoCarrinho(@PathVariable("idCliente") long idCliente, @PathVariable("idProduto") long idProduto, @RequestBody int numItens, @PathVariable String formaEntrega) {
        return carrinhoService.adicionarResumoByIds(idCliente, idProduto, numItens,formaEntrega);
    }

    @RequestMapping(value = "/carrinho/{idCliente}/{idProduto}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeDoCarrinho(@PathVariable("idCliente") long idCliente, @PathVariable("idProduto") long idProduto){
        return carrinhoService.removerResumoCadastradoByIds(idCliente, idProduto);
    }



}