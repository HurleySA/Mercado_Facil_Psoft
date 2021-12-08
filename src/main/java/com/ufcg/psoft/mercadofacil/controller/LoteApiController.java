package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteApiController {

	@Autowired
	LoteService loteService;
	
	@Autowired
	ProdutoService produtoService;
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotesResponse() {
		return loteService.listarLotesResponse();

	}
	@RequestMapping(value = "/lotes/{idProduto}", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotesDeProduto(@PathVariable("idProduto") long idProduto) {
		return loteService.getLoteByProdutoIdResponse(idProduto);
	}
	
	@RequestMapping(value = "/produto/{idProduto}/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("idProduto") long idProduto, @RequestBody int numItens) {
		return loteService.criaLoteById(idProduto, numItens);
	}
}