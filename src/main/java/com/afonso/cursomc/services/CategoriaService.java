package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository rCategoria;

    public Categoria find(Integer id) {
        Optional<Categoria> oCategoria = rCategoria.findById(id);
        return oCategoria.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria oCategoria) {
        oCategoria.setId(null);
        return rCategoria.save(oCategoria);
    }

    public Categoria update(Categoria oCategoria) {
        find(oCategoria.getId());
        return rCategoria.save(oCategoria);
    }
}
