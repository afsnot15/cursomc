package com.afonso.cursomc;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.repositories.CategoriaRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
    
    @Autowired
    private CategoriaRepository rCategoria;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        ArrayList vCategoria = new ArrayList<Categoria>();
        
        Categoria oCategoria = new Categoria();
        oCategoria.setId(null);
        oCategoria.setNome("Informática");
        
        vCategoria.add(oCategoria);
        
        oCategoria = new Categoria();
        oCategoria.setId(null);
        oCategoria.setNome("Fiscal");
        
        vCategoria.add(oCategoria);
        
        oCategoria = new Categoria();
        oCategoria.setId(null);
        oCategoria.setNome("Contabilidade");
        
        vCategoria.add(oCategoria);
        
        rCategoria.saveAll(vCategoria);
    }
}
