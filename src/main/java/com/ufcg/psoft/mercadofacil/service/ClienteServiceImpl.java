package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente getClienteById(Long id) {

		return clienteRepository.findById(id).orElseThrow( () -> new RuntimeException("Cliente não encontrado."));
	}
	@Override
	public ResponseEntity<?> pegaClientePeloId(long id) {
		Cliente cliente = this.getClienteById(id);

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	public Optional<Cliente> getClienteByCPF(Long cpf) {
		return clienteRepository.findByCPF(cpf);
	}

	public ResponseEntity<?> removerClienteCadastradoById(Long id){
		Cliente cliente = this.getClienteById(id);
		this.clienteRepository.delete(cliente);

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}


	public void salvarClienteCadastrado(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public ResponseEntity<?> listarClientesResponse() {
		List<Cliente> clientes = clienteRepository.findAll();

		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}

		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}

	public ResponseEntity<?> criaCliente(ClienteDTO clienteDTO) {
		Optional<Cliente> clienteOp = this.getClienteByCPF(clienteDTO.getCPF());

		if (clienteOp.isPresent()) {
			return ErroCliente.erroClienteJaCadastrado(clienteDTO);
		}

		Cliente cliente = new Cliente(clienteDTO.getCPF(), clienteDTO.getNome(),
				clienteDTO.getIdade(), clienteDTO.getEndereco());
		this.salvarClienteCadastrado(cliente);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
	}

	public  ResponseEntity<?> atualizaClienteById(Long id,ClienteDTO clienteDTO){
		Cliente cliente = this.getClienteById(id);
		cliente.setIdade(clienteDTO.getIdade());
		cliente.setEndereco(clienteDTO.getEndereco());
		this.salvarClienteCadastrado(cliente);


		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	public Cliente atualizaResumosCliente(Resumo resumo, Cliente cliente) {
		cliente.adicionaResumoCarrinho(resumo);
		return cliente;
	}

	@Override
	public Cliente removerResumosCliente(Resumo resumo, Cliente cliente) {
		cliente.removeResumoCarrinho(resumo);
		return cliente;
	}

	@Override
	public Cliente limpaCarrinho(Cliente cliente) {
		cliente.limpaCarrinho();
		return cliente;
	}

	public Cliente atualizaComprasCliente(Compra compra, Cliente cliente) {
		cliente.adicionaCompra(compra);
		return cliente;
	}



}
