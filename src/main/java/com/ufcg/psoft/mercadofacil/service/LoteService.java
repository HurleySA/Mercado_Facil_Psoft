package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;

public interface LoteService {
	public List<Lote> getByProduto(Produto produto);

	public AtomicInteger getTotalByProduto(Produto produto);

	public List<Lote> listarLotes();

	public void salvarLote(Lote lote);

	public void removerLote(Lote lote);
	
	public Lote criaLote(int numItens, Produto produto);

	public Lote getLoteByProduto(Produto produto);

	public Lote atualizaLote(Lote lote, int quantidade);

}
