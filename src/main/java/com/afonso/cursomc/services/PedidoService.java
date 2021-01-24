package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.ItemPedido;
import com.afonso.cursomc.domain.PagamentoComBoleto;
import com.afonso.cursomc.domain.Pedido;
import com.afonso.cursomc.domain.enums.EstadoPagamento;
import com.afonso.cursomc.repositories.ItemPedidoRepository;
import com.afonso.cursomc.repositories.PagamentoRepository;
import com.afonso.cursomc.repositories.PedidoRepository;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService oBoletoService;

    @Autowired
    private PagamentoRepository oPagamentoRepository;
    
    @Autowired
    private ProdutoService oProdutoService;
    
    @Autowired
    private ItemPedidoRepository oItemPedidoRepository;
    
     @Autowired
    private ClienteService oClienteService;

    public Pedido find(Integer id) {
        Optional<Pedido> oPedido = repository.findById(id);
        return oPedido.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido pPedido) {
        pPedido.setId(null);
        pPedido.setInstante(new Date());
        pPedido.setCliente(oClienteService.find(pPedido.getCliente().getId()));
        pPedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pPedido.getPagamento().setPedido(pPedido);

        if (pPedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto oPagameno = (PagamentoComBoleto) pPedido.getPagamento();
            oBoletoService.preencherPagamentoComBoleto(oPagameno, pPedido.getInstante());
        }
        pPedido =  repository.save(pPedido);
        oPagamentoRepository.save(pPedido.getPagamento());
        
        for (ItemPedido oItem : pPedido.getItens()) {
            oItem.setDesconto(0.0);
            oItem.setProduto(oProdutoService.find(oItem.getProduto().getId()));
            oItem.setPreco(oItem.getProduto().getPreco());
            oItem.setPedido(pPedido);
        }
        
        oItemPedidoRepository.saveAll(pPedido.getItens());
        
        System.out.println(pPedido);
        return pPedido;
    }
}
