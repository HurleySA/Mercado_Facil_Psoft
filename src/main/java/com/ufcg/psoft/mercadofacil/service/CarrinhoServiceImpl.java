package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.model.Calculo.Calculo;
import com.ufcg.psoft.mercadofacil.model.Cliente.Cliente;
import com.ufcg.psoft.mercadofacil.model.FormaEntrega.FormaEntrega;
import com.ufcg.psoft.mercadofacil.model.FormaEntrega.FormaEntregaExpress;
import com.ufcg.psoft.mercadofacil.model.FormaEntrega.FormaEntregaPadr√£o;
import com.ufcg.psoft.mercadofacil.model.FormaEntrega.FormaEntregaRetirada;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    private ResumoServiceImpl resumoService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    LoteService loteService;


    CarrinhoRepository carrinhoRepository;

    @Override
    public List<Resumo> listarCarrinho() {
        return resumoService.listarResumos();
    }
    @Override
    public List<Resumo> listarCarrinhoCliente(Cliente cliente) { return carrinhoRepository.findByCliente(cliente);}
    @Override
    public void adicionarResumo(Resumo resumo) {
         resumoService.salvarResumo(resumo);
    }

    @Override
    public void removerResumoCadastrado(Resumo resumo) {
        resumoService.removerResumo(resumo);
    }

    @Override
    public int getTotalProduto(Cliente cliente, Produto produto) {
        List<Resumo> resumos = carrinhoRepository.findByCliente(cliente);
        AtomicInteger total = new AtomicInteger();
        resumos.forEach(resumo -> {
            if(resumo.getProduto().getId() == produto.getId()){
                total.addAndGet(resumo.getQuantidade());
            }

        });
        return total.get();
    }

    @Override
    public ResponseEntity<?> listaCarrinhoByClienteId(long idCliente) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        Carrinho carrinho = cliente.getCarrinho();
        List<Resumo> resumos = carrinho.getResumosPedidos();


        return new ResponseEntity<List<Resumo>>(resumos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> adicionarResumoByIds(long idCliente, long idProduto, int numItens, String formaEntrega) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        Produto produto = produtoService.getProdutoById(idProduto);
        produtoService.verificaDisponibilidade(produto);

        List<String> entregasPermitidos = Arrays.asList("Retirada", "Padr√£o", "Express");
        if (!entregasPermitidos.contains(formaEntrega)) {
            throw new RuntimeException("Forma de entrega n√£o cadastrado.");
        }

        FormaEntrega newFormaEntrega = getFormaEntregaByName(formaEntrega);

        List<Resumo> resumos = resumoService.getResumoByProduto(produto);
        Resumo resumo;
        if(!resumos.isEmpty()){
            Boolean resumoCadastrado = !resumoService.getResumoByProdutoAndCliente(produto, cliente).isEmpty();
            if(resumoCadastrado){
                Boolean existeResumoN√£oComprado = resumoService.verificaSeH√°ResumoN√£oComprado(resumoService.getResumoByProdutoAndCliente(produto, cliente));
                if(existeResumoN√£oComprado){
                    return new ResponseEntity<CustomErrorType>(new CustomErrorType("RESUMO J√Ā CADASTRADO"), HttpStatus.BAD_REQUEST);
                }

                int total = loteService.getTotalByProduto(produto);
                if(numItens > total){
                    return new ResponseEntity<CustomErrorType>(new CustomErrorType("N√ÉO H√Ā TANTAS UNIDADES DISPON√ćVEL"), HttpStatus.BAD_REQUEST);
                }
            }
        }else{
            int total = loteService.getTotalByProduto(produto);


            if(numItens > total){
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("N√ÉO H√Ā TANTAS UNIDADES DISPON√ćVEL"), HttpStatus.BAD_REQUEST);
            }
        }
        resumo = resumoService.criaResumo(numItens, produto, cliente);
        resumoService.salvarResumo(resumo);
        List<Resumo> newResumos = resumoService.getResumoByCliente(cliente);
        List<Resumo> resumosNaoComprados = resumoService.getResumosNaoComprados(newResumos);
        newFormaEntrega.modificaEstrategia(resumosNaoComprados);
        clienteService.atualizaResumosCliente(resumo, cliente);
        clienteService.atualizaFormaEntrega(newFormaEntrega, cliente);
        clienteService.salvarClienteCadastrado(cliente);

        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    private FormaEntrega getFormaEntregaByName(String formaEntrega) {
        if(formaEntrega.equals("Retirada")){
            return new FormaEntregaRetirada();
        }else if(formaEntrega.equals("Padr√£o")){
            return  new FormaEntregaPadr√£o();
        } else{
            return new FormaEntregaExpress();
        }
    }

    @Override
    public ResponseEntity<?> removerResumoCadastradoByIds(long idCliente, long idProduto) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        Produto produto = produtoService.getProdutoById(idProduto);
        List<Resumo> resumos = resumoService.getResumoByProduto(produto);

        if(resumos.isEmpty() ||  cliente.getCarrinho().getResumosPedidos().size() == 0  || resumoService.getResumoByProdutoAndCliente(produto, cliente).isEmpty()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("N√ÉO TEM O ITEM NO CARRINHO."), HttpStatus.BAD_REQUEST);
        }else{
            Resumo resumo = resumoService.getResumoByProdutoAndCliente(produto, cliente).get(resumoService.getResumoByProdutoAndCliente(produto, cliente).size() - 1);
            clienteService.removerResumosCliente(resumo, cliente);
            clienteService.salvarClienteCadastrado(cliente);
            resumoService.removerResumo(resumo);

        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> calculaEntrega(long idCliente) {
        Cliente cliente = clienteService.getClienteById(idCliente);

        double total = cliente.getCarrinho().calculaEntrega();

        return new ResponseEntity<Double>(total, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> modificaFormaEntrega(long idCliente, String formaEntrega) {
        Cliente cliente = clienteService.getClienteById(idCliente);

        List<String> entregasPermitidos = Arrays.asList("Retirada", "Padr√£o", "Express");
        if (!entregasPermitidos.contains(formaEntrega)) {
            throw new RuntimeException("Forma de entrega n√£o cadastrado.");
        }
        FormaEntrega newFormaEntrega;
        Calculo calculo = cliente.getCarrinho().getFormaEntrega().getEstrategia();
        if(formaEntrega.equals("Retirada")){
            newFormaEntrega = new FormaEntregaRetirada(calculo);
        }else if(formaEntrega.equals("Padr√£o")){
            newFormaEntrega = new FormaEntregaPadr√£o(calculo);
        } else{
            newFormaEntrega = new FormaEntregaExpress(calculo);
        }
        clienteService.atualizaFormaEntrega(newFormaEntrega, cliente);
        clienteService.salvarClienteCadastrado(cliente);

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

}
