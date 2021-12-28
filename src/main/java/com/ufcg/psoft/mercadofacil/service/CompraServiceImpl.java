package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Resumo;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class CompraServiceImpl implements CompraService{
    @Autowired
    private CompraRepository compraRepository;

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

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(long id) { return compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra não existente."));
    }
    public List<Compra> getComprasByCliente(Cliente cliente) {return compraRepository.findByCliente(cliente);}

    public Compra criaCompra(List<Resumo> resumos, int quantidadeProdutos, String data,String formaPagamento,Cliente cliente){
        Compra compra = new Compra(resumos, quantidadeProdutos, data,formaPagamento, cliente);
        return compra;
    }

    public void salvarCompra(Compra compra){ compraRepository.save(compra);}

    public void removerCompra(Compra compra){ compraRepository.delete(compra);}

    @Override
    public ResponseEntity<?> criaCompraById(long idCliente, String formaPagamento) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        verificaFormaPagamento(formaPagamento);
        List<Resumo> resumos = resumoService.getResumoByCliente(cliente);
        List<Resumo> resumosNaoComprados = resumoService.getResumosNaoComprados(resumos);
        resumosNaoComprados.forEach(resumo -> {
                if(resumo.getQuantidade() == loteService.getTotalByProduto(resumo.getProduto()).get()){
                    loteService.removerLote(loteService.getLoteByProduto(resumo.getProduto()));
                }else{
                    if(resumo.getQuantidade() < loteService.getTotalByProduto(resumo.getProduto()).get()){
                        loteService.atualizaLote(loteService.getLoteByProduto(resumo.getProduto()), resumo.getQuantidade());
                    }

                }

        });
        Compra compra = compraService.criaCompra(resumosNaoComprados, resumos.size(), java.time.LocalDate.now().toString(), formaPagamento , cliente);

        resumos.forEach(resumo -> {
            if(!resumo.getComprado()){
                resumo.setComprado(true);
            }
        });
        compraService.salvarCompra(compra);
        clienteService.atualizaComprasCliente(compra, cliente);
        clienteService.limpaCarrinho(cliente);
        clienteService.salvarClienteCadastrado(cliente);



        return new ResponseEntity<  List<Resumo>>(resumos, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> listarComprasResponse(Long idCliente) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        List<Compra> compras = cliente.getCompras();

        if(compras.size() == 0){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("NÃO POSSUI COMPRAS."), HttpStatus.CONFLICT);

        }
        return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> listarComprasByIds(long idCliente, long idCompra) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        Compra compra = this.getCompraById(idCompra);
        this.verificaCompraDeCliente(cliente, compra);
        return new ResponseEntity<Compra>(compra, HttpStatus.OK);
    }

    private void verificaCompraDeCliente(Cliente cliente, Compra compra) {
        if(!cliente.getCompras().contains(compra)){
            throw new RuntimeException("Compra não pertence ao cliente.");
        }
    }

    private boolean verificaFormaPagamento(String pagamento){
        List<String> formasPagamento = Arrays.asList("Cartão de Crédito", "Boleto", "Paypal");
        Boolean contain = formasPagamento.contains(pagamento);
        if(!contain){
            throw new RuntimeException("Forma de pagamento não aceita");
        }
        return contain;
    }

}
