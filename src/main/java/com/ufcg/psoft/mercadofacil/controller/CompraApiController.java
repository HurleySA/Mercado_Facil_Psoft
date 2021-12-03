package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraApiController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    CompraService compraService;

    @RequestMapping(value = "/compras/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> listaCompras(@PathVariable("idCliente") long idCliente){
        Optional<Cliente> cliente = clienteService.getClienteById(idCliente);

        if (!cliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        List<Compra> compras = cliente.get().getCompras();

        if(compras.size() == 0){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO POSSUI COMPRAS."), HttpStatus.CONFLICT);

        }


        return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);

    }


}
