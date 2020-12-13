package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Produto;
import com.afonso.cursomc.repositories.ProdutoRepository;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository rProduto;
    
    public Produto find(Integer id) {
        Optional<Produto> oProduto = rProduto.findById(id);
        return oProduto.orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }
}
