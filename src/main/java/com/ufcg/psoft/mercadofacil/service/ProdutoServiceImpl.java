package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto getProdutoById(long id) {
		return produtoRepository.findById(id).orElseThrow(()-> new RuntimeException("Produto não encontrado."));
	}
	
	public List<Produto> getProdutoByCodigoBarra(String codigo) {
		return produtoRepository.findByCodigoBarra(codigo);
	}

	public ResponseEntity<?>  listaProdutosResponse(){
		List<Produto> produtos = produtoRepository.findAll();

		if (produtos.isEmpty()) {
			return ErroProduto.erroSemProdutosCadastrados();
		}

		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	public void removerProdutoCadastrado(Produto produto) {
		produtoRepository.delete(produto);
	}

	public void salvarProdutoCadastrado(Produto produto) {
		produtoRepository.save(produto);
	}

	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}

	public ResponseEntity<?> criaProduto(ProdutoDTO produtoDTO) {
		List<Produto> produtos = this.getProdutoByCodigoBarra(produtoDTO.getCodigoBarra());

		if (!produtos.isEmpty()) {
			return ErroProduto.erroProdutoJaCadastrado(produtoDTO);
		}

		Produto produto = new Produto(produtoDTO.getNome(), produtoDTO.getFabricante(), produtoDTO.getCodigoBarra(),
				produtoDTO.getPreco(), produtoDTO.getCategoria());
		this.salvarProdutoCadastrado(produto);

		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}

	@Override
	public Produto atualizaProduto(ProdutoDTO produtoDTO, Produto produto) {
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());
		produto.setCodigoBarra(produtoDTO.getCodigoBarra());
		produto.mudaFabricante(produtoDTO.getFabricante());
		produto.mudaCategoria(produtoDTO.getCategoria());

		return produto;
	}

	public ResponseEntity<?> atualizaProdutoById(Long id, ProdutoDTO produtoDTO) {
		Produto produto = this.getProdutoById(id);

		this.atualizaProduto(produtoDTO, produto);
		this.salvarProdutoCadastrado(produto);

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> getProduto(long id) {
		Optional<Produto> optionalProduto = produtoRepository.findById(id);

		if (!optionalProduto.isPresent()) {
			return ErroProduto.erroProdutoNaoEnconrtrado(id);
		}

		return new ResponseEntity<Produto>(optionalProduto.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removerProdutoCadastradoById(long id) {
		Produto produto = this.getProdutoById(id);

		produtoRepository.delete(produto);

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> listaProdutosByCodigoBarra(String codigo) {
		List<Produto> produtos = this.getProdutoByCodigoBarra(codigo);

		if(produtos.isEmpty()){
			return ErroProduto.erroSemProdutosCadastrados();
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@Override
	public void verificaDisponibilidade(Produto produto) {
		if(!produto.isDisponivel()){
			throw new RuntimeException("Produto não disponivel.");
		}

	}

	@Override
	public void tornaDisponivel(Produto produto) {
		produto.tornaDisponivel();
		this.salvarProdutoCadastrado(produto);
	}
}
