package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.ItemPedido;
import com.afonso.cursomc.domain.PagamentoComBoleto;
import com.afonso.cursomc.domain.Pedido;
import com.afonso.cursomc.domain.enums.EstadoPagamento;
import com.afonso.cursomc.repositories.ItemPedidoRepository;
import com.afonso.cursomc.repositories.PagamentoRepository;
import com.afonso.cursomc.repositories.PedidoRepository;
import com.afonso.cursomc.security.UserSS;
import com.afonso.cursomc.services.exception.AuthorizationException;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

    @Autowired
    private EmailService oEmailService;

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
        pPedido = repository.save(pPedido);
        oPagamentoRepository.save(pPedido.getPagamento());

        for (ItemPedido oItem : pPedido.getItens()) {
            oItem.setDesconto(0.0);
            oItem.setProduto(oProdutoService.find(oItem.getProduto().getId()));
            oItem.setPreco(oItem.getProduto().getPreco());
            oItem.setPedido(pPedido);
        }

        oItemPedidoRepository.saveAll(pPedido.getItens());

        oEmailService.sendOrderConfirmationHtmlEmail(pPedido);
        return pPedido;
    }

    public Page<Pedido> findPage(Integer pPage, Integer pLinesPerPage, String pOrderBy, String pDirection) {
        UserSS user = UserService.authenticated();
        
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
       
        PageRequest pageRequest = PageRequest.of(pPage, pLinesPerPage, Direction.valueOf(pDirection), pOrderBy);
        Cliente oCliente = oClienteService.find(user.getId());
        return repository.findByCliente(oCliente, pageRequest);
    }
}
