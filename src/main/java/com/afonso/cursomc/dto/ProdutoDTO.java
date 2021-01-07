package com.afonso.cursomc.dto;

import com.afonso.cursomc.domain.Produto;

/**
 *
 * @author Afonso
 */
public class ProdutoDTO {

    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto oProduto) {
        id = oProduto.getId();
        nome = oProduto.getDescricao();
        preco = oProduto.getPreco();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
