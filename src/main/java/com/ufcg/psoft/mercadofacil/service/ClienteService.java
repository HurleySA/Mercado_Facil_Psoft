package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.http.ResponseEntity;

public interface ClienteService {

	public Cliente getClienteById(Long id);
	
	public Optional<Cliente> getClienteByCPF(Long cpf);

	public ResponseEntity<?> removerClienteCadastradoById(Long id);


	public void salvarClienteCadastrado(Cliente cliente);

	public List<Cliente> listarClientes();

	public ResponseEntity<?> listarClientesResponse();
	
	public ResponseEntity<?> criaCliente(ClienteDTO clienteDTO);

	public  ResponseEntity<?> atualizaClienteById(Long id,ClienteDTO clienteDTO);

	public Cliente atualizaResumosCliente(Resumo resumo, Cliente cliente);

	public Cliente removerResumosCliente(Resumo resumo, Cliente cliente);

	public Cliente limpaCarrinho(Cliente cliente);

	public Cliente atualizaComprasCliente(Compra compra, Cliente cliente);
	ResponseEntity<?> pegaClientePeloId(long id);

	public Cliente atualizaFormaEntrega(String formaEntrega, Cliente cliente);
}
