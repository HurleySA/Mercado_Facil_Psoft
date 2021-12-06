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
        Optional<Cliente> cliente = clienteService.getClienteById(idCliente);

        if (!cliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Carrinho carrinho = cliente.get().getCarrinho();
        List<Resumo> resumos = carrinho.getResumosPedidos();


        return new ResponseEntity<List<Resumo>>(resumos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> adicionarResumoByIds(long idCliente, long idProduto, int numItens) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idProduto);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(idProduto);
        }
        Produto produto = optionalProduto.get();

        if(!produto.isDisponivel()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("PRODUTO NÃO DISPONÍVEL"), HttpStatus.CONFLICT);
        }

        List<Resumo> resumos = resumoService.getResumoByProduto(produto);
        Resumo resumo;
        if(!resumos.isEmpty()){
            Boolean resumoCadastrado = resumoService.getResumoByProdutoAndCliente(produto, cliente).isPresent();
            int total = loteService.getTotalByProduto(produto).get();

            if(resumoCadastrado){
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("RESUMO JÁ CADASTRADO"), HttpStatus.CONFLICT);
            }
            if(numItens > total){
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO HÁ TANTAS UNIDADES DISPONÍVEL"), HttpStatus.CONFLICT);
            }
            resumo = resumoService.criaResumo(numItens, produto, cliente);
            resumoService.salvarResumo(resumo);
            clienteService.atualizaResumosCliente(resumo, cliente);
            clienteService.salvarClienteCadastrado(cliente);
        }else{
            resumo = resumoService.criaResumo(numItens, produto, cliente);
            resumoService.salvarResumo(resumo);
            clienteService.atualizaResumosCliente(resumo, cliente);
            clienteService.salvarClienteCadastrado(cliente);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removerResumoCadastradoByIds(long idCliente, long idProduto) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(idCliente);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
        }
        Cliente cliente = optionalCliente.get();

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idProduto);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEnconrtrado(idProduto);
        }

        Produto produto = optionalProduto.get();

        List<Resumo> optionalResumo = resumoService.getResumoByProduto(produto);


        if(optionalResumo.isEmpty() ||  cliente.getCarrinho().getResumosPedidos().size() == 0  || !resumoService.getResumoByProdutoAndCliente(produto, cliente).isPresent()){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO TEM O ITEM NO CARRINHO."), HttpStatus.CONFLICT);
        }else{
            Resumo resumo = resumoService.getResumoByProdutoAndCliente(produto, cliente).get();
            clienteService.removerResumosCliente(resumo, cliente);
            clienteService.salvarClienteCadastrado(cliente);
            resumoService.removerResumo(resumo);

        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

    }

}
