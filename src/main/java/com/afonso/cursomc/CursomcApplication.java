package com.afonso.cursomc;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.domain.Produto;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.repositories.ProdutoRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository rCategoria;

    @Autowired
    private ProdutoRepository rProdutoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Categoria> vCategoria = new ArrayList<>();
        ArrayList<Produto> vProduto = new ArrayList<>();

        Produto oProduto1 = new Produto();
        oProduto1.setDescricao("Computador");
        oProduto1.setPreco(2000.00);

        vProduto.add(oProduto1);

        Produto oProduto2 = new Produto();
        oProduto2.setDescricao("Impressora");
        oProduto2.setPreco(800.00);

        vProduto.add(oProduto2);

        Produto oProduto3 = new Produto();
        oProduto3.setDescricao("Mouse");
        oProduto3.setPreco(80.00);

        vProduto.add(oProduto3);

        Categoria oCategoria1 = new Categoria();
        oCategoria1.setId(null);
        oCategoria1.setNome("Informática");

        vCategoria.add(oCategoria1);

        Categoria oCategoria2 = new Categoria();
        oCategoria2.setId(null);
        oCategoria2.setNome("Contabilidade");

        vCategoria.add(oCategoria2);

        oCategoria1.getvProduto().addAll(vProduto);
        oCategoria2.getvProduto().add(oProduto2);
        
        oProduto1.getvCategoria().add(oCategoria1);
        oProduto2.getvCategoria().addAll(vCategoria);
        oProduto3.getvCategoria().add(oCategoria1);

        rCategoria.saveAll(vCategoria);
        rProdutoRepository.saveAll(vProduto);
    }
}
