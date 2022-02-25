package com.ufcg.psoft.mercadofacil.model.Cliente;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.FormaEntrega.FormaEntrega;
import com.ufcg.psoft.mercadofacil.model.Resumo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long CPF;
	
	private String nome;

	private Integer idade;

	private String endereco;

	private String perfil;

	@OneToOne(cascade=CascadeType.ALL)
	private Carrinho carrinho;

	@OneToMany(mappedBy="cliente")
	@JsonManagedReference
	private List<Compra> compras;

	public Cliente() {}

	public Cliente(Long cpf, String nome, Integer idade, String endereco, String perfil) {
		this.CPF = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.carrinho = new Carrinho();
		this.compras = new ArrayList<Compra>();
		this.perfil = perfil;
	}

	public Cliente(Long cpf, String nome, Integer idade, String endereco, Carrinho carrinho, List<Compra> compras, String perfil) {
		this.CPF = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.carrinho = carrinho;
		this.compras =  new ArrayList<Compra>(compras);
		this.perfil = perfil;
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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
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

	public void adicionaResumoCarrinho(Resumo resumo) {this.carrinho.adicionaResumo(resumo);}

	public void removeResumoCarrinho(Resumo resumo) {this.carrinho.removeResumo(resumo);}

	public void adicionaCompra(Compra compra) {this.compras.add(compra);}

	public void removeCompra(Compra compra) {this.compras.remove(compra);}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public void limpaCarrinho() {
		this.carrinho.limpaCarrinho();
	}

	public abstract double descontoCompras(double valor, int quantidade);

	public void atualizaFormaEntrega(FormaEntrega formaEntrega) {
		{this.carrinho.setFormaEntrega(formaEntrega);}
	}
}
