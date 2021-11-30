package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ResumoService;
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
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {

    CarrinhoService carrinhoService;

    @Autowired
    ResumoService resumoService;


    @RequestMapping(value = "/carrinho", method = RequestMethod.GET)
    public ResponseEntity<?> listarCarrinho() {

        List<Resumo> carrinho = carrinhoService.listarCarrinho();

        if (carrinho.isEmpty()) {
            return ErroLote.erroSemLotesCadastrados();
        }

        return new ResponseEntity<List<Resumo>>(carrinho, HttpStatus.OK);

    }

    @RequestMapping(value = "/carrinho/{idResumo}", method = RequestMethod.POST)
    public ResponseEntity<?> adicionaResumo(@PathVariable("idResumo") long id) {

        Optional<Resumo> optionalResumo = resumoService.getResumoById(id);

        if (!optionalResumo.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(id);
        }

        Resumo resumo = optionalResumo.get();
        carrinhoService.adicionarResumo(resumo);

        return new ResponseEntity<>(resumo, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/carrinho/{idResumo}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeResumo(@PathVariable("idResumo") long id) {

        Optional<Resumo> optionalResumo = resumoService.getResumoById(id);

        if (!optionalResumo.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(id);
        }

        Resumo resumo = optionalResumo.get();
        carrinhoService.removerResumoCadastrado(resumo);

        return new ResponseEntity<>(resumo, HttpStatus.OK);
    }
}