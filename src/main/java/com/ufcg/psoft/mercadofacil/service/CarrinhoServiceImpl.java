package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

        List<Resumo> resumos = resumoService.getResumoByProduto(produto);
        Resumo resumo;
        if(!resumos.isEmpty()){
            Boolean resumoCadastrado = !resumoService.getResumoByProdutoAndCliente(produto, cliente).isEmpty();
            if(resumoCadastrado){
                Boolean existeResumoNãoComprado = resumoService.verificaSeHáResumoNãoComprado(resumoService.getResumoByProdutoAndCliente(produto, cliente));
                if(existeResumoNãoComprado){
                    return new ResponseEntity<CustomErrorType>(new CustomErrorType("RESUMO JÁ CADASTRADO"), HttpStatus.BAD_REQUEST);
                }

                int total = loteService.getTotalByProduto(produto);


                if(numItens > total){
                    return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO HÁ TANTAS UNIDADES DISPONÍVEL"), HttpStatus.BAD_REQUEST);
                }
            }

            resumo = resumoService.criaResumo(numItens, produto, cliente);
            resumoService.salvarResumo(resumo);
            clienteService.atualizaResumosCliente(resumo, cliente);
            clienteService.atualizaFormaEntrega(formaEntrega, cliente);
            clienteService.salvarClienteCadastrado(cliente);
        }else{
            int total = loteService.getTotalByProduto(produto);


            if(numItens > total){
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO HÁ TANTAS UNIDADES DISPONÍVEL"), HttpStatus.BAD_REQUEST);
            }
            resumo = resumoService.criaResumo(numItens, produto, cliente);
            resumoService.salvarResumo(resumo);
            clienteService.atualizaResumosCliente(resumo, cliente);
            clienteService.atualizaFormaEntrega(formaEntrega, cliente);
            clienteService.salvarClienteCadastrado(cliente);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> removerResumoCadastradoByIds(long idCliente, long idProduto) {
        Cliente cliente = clienteService.getClienteById(idCliente);

        Produto produto = produtoService.getProdutoById(idProduto);

        List<Resumo> resumos = resumoService.getResumoByProduto(produto);


        if(resumos.isEmpty() ||  cliente.getCarrinho().getResumosPedidos().size() == 0  || resumoService.getResumoByProdutoAndCliente(produto, cliente).isEmpty()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO TEM O ITEM NO CARRINHO."), HttpStatus.BAD_REQUEST);
        }else{
            Resumo resumo = resumoService.getResumoByProdutoAndCliente(produto, cliente).get(resumoService.getResumoByProdutoAndCliente(produto, cliente).size() - 1);
            clienteService.removerResumosCliente(resumo, cliente);
            clienteService.salvarClienteCadastrado(cliente);
            resumoService.removerResumo(resumo);

        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

    }

}
