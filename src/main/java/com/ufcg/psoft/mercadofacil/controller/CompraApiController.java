package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.service.*;
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

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    ResumoService resumoService;

    @Autowired
    LoteService loteService;

    @RequestMapping(value="/compra/{idCliente}", method = RequestMethod.POST)
    public ResponseEntity<?> realizaCompra(@PathVariable("idCliente") long idCliente){
        return compraService.criaCompraById(idCliente);
    }

    @RequestMapping(value = "/compras/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> listaCompras(@PathVariable("idCliente") long idCliente){
        return compraService.listarComprasResponse(idCliente);
    }

    @RequestMapping(value = "/compras/{idCliente}/{idCompra}", method = RequestMethod.GET)
    public ResponseEntity<?> listaCompras(@PathVariable("idCliente") long idCliente, @PathVariable("idCompra") long idCompra){
        return compraService.listarComprasByIds(idCliente, idCompra);
    }

}
