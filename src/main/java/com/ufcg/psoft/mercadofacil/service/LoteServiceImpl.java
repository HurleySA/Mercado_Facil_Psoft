package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

import javax.persistence.EntityExistsException;

@Service
public class LoteServiceImpl implements LoteService {
	
	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	ProdutoService produtoService;

	@Override
	public List<Lote> getByProduto(Produto produto) {
		return loteRepository.findByProduto(produto);
	}

	@Override
	public Lote getLoteByProduto(Produto produto){ return loteRepository.findLoteByProduto(produto);}

	@Override
	public Lote atualizaLote(Lote lote, int quantidade) {
		lote.setNumeroDeItens(lote.getNumeroDeItens() - quantidade);
		return  lote;
	}

	@Override
	public ResponseEntity<?> getLoteByProdutoIdResponse(long idProduto) {
		Produto produto = produtoService.getProdutoById(idProduto);

		List<Lote> lotes = this.getByProduto(produto);

		if (lotes.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}


		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}

	@Override
	public List<Lote> getLoteByProdutoId(long idProduto) {
		Produto produto = produtoService.getProdutoById(idProduto);

		return this.getByProduto(produto);
	}

	@Override
	public ResponseEntity<?> criaLoteById(long idProduto, int numItens) {
		Produto produto = produtoService.getProdutoById(idProduto);
		Lote lote = this.criaLote(numItens, produto);
		produtoService.tornaDisponivel(produto);

		this.salvarLote(lote);

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

	@Override
	public int getTotalByProduto(Produto produto) {
		int totalItem = 0;
		List<Lote> lotes = getByProduto(produto);
		AtomicInteger total = new AtomicInteger();
		for(int i = 0; i < lotes.size(); i++){
			totalItem += lotes.get(i).getNumeroDeItens();
		}
		return totalItem;
	}

	public ResponseEntity<?> listarLotesResponse() {
		List<Lote> lotes = loteRepository.findAll();
		if (lotes.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}

		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}

	@Override
	public List<Lote> listarLotes() {
		return loteRepository.findAll();
	}

	public void salvarLote(Lote lote) {
		loteRepository.save(lote);		
	}
	public void removerLote(Lote lote){
		loteRepository.delete(lote);
	}

	public Lote criaLote(int numItens, Produto produto) {
		if(numItens <= 0){
			throw new RuntimeException("Quantidade abaixo de 1 produto.");
		}
		Lote lote = new Lote(produto, numItens);
		return lote;
	}
}
