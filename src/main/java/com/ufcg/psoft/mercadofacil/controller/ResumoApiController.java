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

}