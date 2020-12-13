package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Pedido;
import com.afonso.cursomc.repositories.PedidoRepository;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repository;
    
    public Pedido find(Integer id) {
        Optional<Pedido> oPedido = repository.findById(id);
        return oPedido.orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
