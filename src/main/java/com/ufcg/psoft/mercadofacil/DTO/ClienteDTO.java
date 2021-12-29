package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.List;

public class ClienteDTO {

	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;

	private String perfil;

	public String getPerfil() { return perfil;}

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


}
