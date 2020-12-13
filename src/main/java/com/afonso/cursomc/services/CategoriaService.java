package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.services.exception.DataIntegratyException;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

    public void delete(Integer id) {
        try {
            rCategoria.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratyException("Não é possível excluir uma categoria que contém produtos");
        }
    }

    public List<Categoria> findAll() {
        return rCategoria.findAll();
    }
    
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return rCategoria.findAll(pageRequest);
    }
}
