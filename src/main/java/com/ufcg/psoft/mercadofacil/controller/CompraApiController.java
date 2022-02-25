package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    @RequestMapping(value = "/compra/pagamento", method = RequestMethod.GET)
    public ResponseEntity<?> listaFormasPagamento(){
        List<String> formasPagamento = Arrays.asList("Cartão de Crédito", "Boleto", "Paypal");
        return new ResponseEntity<List<String>>(formasPagamento, HttpStatus.OK);
    }

    @RequestMapping(value="/compra/{idCliente}", method = RequestMethod.POST)
    public ResponseEntity<?> realizaCompra(@PathVariable("idCliente") long idCliente, @RequestBody String formaPagamento){
        return compraService.criaCompraById(idCliente, formaPagamento);
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
