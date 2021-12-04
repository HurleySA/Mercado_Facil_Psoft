package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long CPF;
	
	private String nome;

	private Integer idade;

	private String endereco;

	@OneToOne(cascade=CascadeType.ALL)
	private Carrinho carrinho;

	@OneToMany(mappedBy="cliente")
	@JsonManagedReference
	private List<Compra> compras;

	public Cliente() {}

	public Cliente(Long cpf, String nome, Integer idade, String endereco) {
		this.CPF = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.carrinho = new Carrinho();
		this.compras = new ArrayList<Compra>();
	}

	public Cliente(Long cpf, String nome, Integer idade, String endereco, Carrinho carrinho, List<Compra> compras) {
		this.CPF = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.carrinho = carrinho;
		this.compras = compras;
	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return CPF;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public void adicionaResumo(Resumo resumo) {this.carrinho.adicionaResumo(resumo);}

	public void removeResumo(Resumo resumo) {this.carrinho.removeResumo(resumo);}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public void limpaCarrinho() {
		this.carrinho.limpaCarrinho();
	}
}
