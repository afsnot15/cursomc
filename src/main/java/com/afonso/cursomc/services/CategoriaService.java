package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.repositories.CategoriaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository rCategoria;
    
    public Categoria buscar(Integer id) {
        Optional<Categoria> oCategoria = rCategoria.findById(id);
        return oCategoria.orElse(null);
    }
}