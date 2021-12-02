package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

public class ClienteDTO {

	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;

	private Carrinho carrinho;

	public String getNome() {
		return nome;
	}
	
	public Long getCPF() {
		return cpf;
	}

	public Integer getIdade() {
		return idade;
	}

	public String getEndereco() { return endereco; }

	public Carrinho getCarrinho() { return carrinho;}

}
