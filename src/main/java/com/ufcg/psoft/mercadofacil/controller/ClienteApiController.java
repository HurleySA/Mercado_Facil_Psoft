package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClienteApiController {

	@Autowired
	ClienteService clienteService;

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ResponseEntity<?> listarClientesResponse() {
		return clienteService.listarClientesResponse();
	}

	@RequestMapping(value = "/cliente/", method = RequestMethod.POST)
	public ResponseEntity<?> criarCliente(@RequestBody ClienteDTO clienteDTO, UriComponentsBuilder ucBuilder) {
		return clienteService.criaCliente(clienteDTO);
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarCliente(@PathVariable("id") long id) {
		return clienteService.pegaClientePeloId(id);
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {
		return clienteService.atualizaClienteById(id, clienteDTO);

	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerCliente(@PathVariable("id") long id) {
		return clienteService.removerClienteCadastradoById(id);

	}
}