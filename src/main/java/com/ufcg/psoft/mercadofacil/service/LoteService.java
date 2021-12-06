package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.http.ResponseEntity;

public interface LoteService {
	public List<Lote> getByProduto(Produto produto);

	public AtomicInteger getTotalByProduto(Produto produto);

	public ResponseEntity<?> listarLotesResponse();

	public List<Lote> listarLotes();

	public void salvarLote(Lote lote);

	public void removerLote(Lote lote);
	
	public Lote criaLote(int numItens, Produto produto);

	public Lote getLoteByProduto(Produto produto);

	public Lote atualizaLote(Lote lote, int quantidade);

	ResponseEntity<?> getLoteByProdutoIdResponse(long idProduto);

	List<Lote>  getLoteByProdutoId(long idProduto);

	ResponseEntity<?> criaLoteById(long idProduto, int numItens);
}
