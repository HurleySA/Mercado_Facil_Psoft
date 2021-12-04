package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    private ResumoServiceImpl resumoService;

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
}
