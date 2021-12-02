package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

@Service
public class LoteServiceImpl implements LoteService {
	
	@Autowired
	private LoteRepository loteRepository;

	@Override
	public List<Lote> getByProduto(Produto produto) {
		return loteRepository.findByProduto(produto);
	}

	@Override
	public AtomicInteger getTotalByProduto(Produto produto) {
		List<Lote> lotes = getByProduto(produto);
		AtomicInteger total = new AtomicInteger();
		lotes.forEach(lote -> {
			total.addAndGet(lote.getNumeroDeItens());
		});
		return total;
	}

	public List<Lote> listarLotes() {
		return loteRepository.findAll();
	}

	public void salvarLote(Lote lote) {
		loteRepository.save(lote);		
	}

	public Lote criaLote(int numItens, Produto produto) {
		Lote lote = new Lote(produto, numItens);
		return lote;
	}
}
