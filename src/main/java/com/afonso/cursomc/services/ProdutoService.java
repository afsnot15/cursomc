package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.domain.Produto;
import com.afonso.cursomc.repositories.ProdutoRepository;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository rProduto;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> oProduto = rProduto.findById(id);
        return oProduto.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String descricao, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return rProduto.findDistinctByDescricaoContainingAndCategoriasIn(descricao, categorias, pageRequest);
    }
}
