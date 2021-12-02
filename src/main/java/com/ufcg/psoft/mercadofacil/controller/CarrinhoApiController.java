package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.service.ResumoService;
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
import com.ufcg.psoft.mercadofacil.service.ClienteService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ResumoService resumoService;


    @RequestMapping(value = "/carrinho/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> consultaCarrinho(@PathVariable("idCliente") long idCliente) {

        Optional<Cliente> cliente = clienteService.getClienteById(idCliente);

        if (!cliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Carrinho carrinho = cliente.get().getCarrinho();
        List<Resumo> resumos = carrinho.getResumosPedidos();


        return new ResponseEntity<List<Resumo>>(resumos, HttpStatus.OK);

    }

    @RequestMapping(value = "/carrinho/{idCliente}/{idProduto}", method = RequestMethod.POST)
    public ResponseEntity<?> adicionaAoCarrinho(@PathVariable("idCliente") long idCliente, @PathVariable("idProduto") long idProduto, @RequestBody int numItens) {

        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idProduto);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(idProduto);
        }
        Produto produto = optionalProduto.get();

        Optional<Resumo> resumoProduto = resumoService.getResumoByProduto(produto);

        if(resumoProduto.isPresent()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("RESUMO JÁ CADASTRADO"), HttpStatus.CONFLICT);
        }

        Resumo resumo = resumoService.criaResumo(numItens, produto);
        resumoService.salvarResumo(resumo);
        clienteService.atualizaResumosCliente(resumo, cliente);
        clienteService.salvarClienteCadastrado(cliente);



        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

    }

    @RequestMapping(value = "/carrinho/{idCliente}/{idProduto}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeDoCarrinho(@PathVariable("idCliente") long idCliente, @PathVariable("idProduto") long idProduto){

        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idProduto);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(idProduto);
        }

        Produto produto = optionalProduto.get();

        Optional<Resumo> optionalResumo = resumoService.getResumoByProduto(produto);
        Resumo resumo = optionalResumo.get();

        clienteService.removerResumosCliente(resumo, cliente);
        clienteService.salvarClienteCadastrado(cliente);




        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }



}