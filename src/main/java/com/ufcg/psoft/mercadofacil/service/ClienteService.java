package com.ufcg.psoft.mercadofacil.service;

import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.http.ResponseEntity;

public interface ClienteService {

	public Optional<Cliente> getClienteById(Long id);
	
	public Optional<Cliente> getClienteByCPF(Long cpf);

	public ResponseEntity<?> removerClienteCadastradoById(Long id);


	public void salvarClienteCadastrado(Cliente cliente);

	public ResponseEntity<?> listarClientes();
	
	public ResponseEntity<?> criaCliente(ClienteDTO clienteDTO);

	public  ResponseEntity<?> atualizaClienteById(Long id,ClienteDTO clienteDTO);

	public Cliente atualizaResumosCliente(Resumo resumo, Cliente cliente);

	public Cliente removerResumosCliente(Resumo resumo, Cliente cliente);

	public Cliente limpaCarrinho(Cliente cliente);


	ResponseEntity<?> pegaClientePeloId(long id);
}
