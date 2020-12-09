package com.afonso.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cidade {
    
    @Id
    @GeneratedValue
    private Integer id = 0;
    private String nome = "";
    
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="id_estado")
    private Estado oEstado;

    public Cidade() {
        
    }
    
    public Cidade(Integer id, String nome, Estado oEstado) {
        super();
        this.id = id;
        this.nome = nome;
        this.oEstado = oEstado;
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

    public Estado getoEstado() {
        return oEstado;
    }

    public void setoEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
