package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ResumoService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ResumoApiController {

    @Autowired
    ResumoService resumoService;

    @Autowired
    ProdutoService produtoService;

    @RequestMapping(value = "/resumos", method = RequestMethod.GET)
    public ResponseEntity<?> listarLotes() {

        List<Resumo> resumos = resumoService.listarResumos();

        if (resumos.isEmpty()) {
            return ErroLote.erroSemLotesCadastrados();
        }

        return new ResponseEntity<List<Resumo>>(resumos, HttpStatus.OK);
    }

    @RequestMapping(value = "/resumo/{idProduto}", method = RequestMethod.POST)
    public ResponseEntity<?> criarResumo(@PathVariable("idProduto") long id, @RequestBody int numItens) {

        Optional<Produto> optionalProduto = produtoService.getProdutoById(id);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(id);
        }

        Produto produto = optionalProduto.get();
        Resumo resumo = resumoService.criaResumo(numItens, produto);

        if (!produto.isDisponivel() || (numItens < 0)) {
            return ErroProduto.erroSemProdutosCadastrados();
        }

        resumoService.salvarResumo(resumo);

        return new ResponseEntity<>(resumo, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/resumo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removerResumo(@PathVariable("id") long id) {

        Optional<Resumo> optionalResumo = resumoService.getResumoById(id);

        if (!optionalResumo.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(id);
        }

        resumoService.removerResumo(optionalResumo.get());

        return new ResponseEntity<Resumo>(HttpStatus.OK);
    }
}