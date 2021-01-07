package com.afonso.cursomc.repositories;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.domain.Produto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    /*@Query("SELECT DISTINCT obj "
            + "FROM Produto obj "
            + "INNER JOIN obj.vCategoria cat "
            + "WHERE obj.descricao LIKE %:descricao% AND cat IN :vCategoria")*/
    @Transactional(readOnly = true)
    Page<Produto> findDistinctByDescricaoContainingAndCategoriasIn(String descricao, List<Categoria> categorias, Pageable pageRequest);
}
