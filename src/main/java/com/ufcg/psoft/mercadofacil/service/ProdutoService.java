package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.http.ResponseEntity;

public interface ProdutoService {

	public Produto getProdutoById(long id);
	
	public List<Produto> getProdutoByCodigoBarra(String codigo);
	
	public void removerProdutoCadastrado(Produto produto);

	public void salvarProdutoCadastrado(Produto produto);

	public List<Produto> listarProdutos();

	public ResponseEntity<?>  listaProdutosResponse();
	
	public ResponseEntity<?> criaProduto(ProdutoDTO produto);
	
	public Produto atualizaProduto(ProdutoDTO produtoDTO, Produto produto);
	public ResponseEntity<?> atualizaProdutoById(Long id, ProdutoDTO produtoDTO);

	ResponseEntity<?> getProduto(long id);

	ResponseEntity<?> removerProdutoCadastradoById(long id);

	ResponseEntity<?> listaProdutosByCodigoBarra(String codigo);

	void verificaDisponibilidade(Produto produto);

	void tornaDisponivel(Produto produto);
}
