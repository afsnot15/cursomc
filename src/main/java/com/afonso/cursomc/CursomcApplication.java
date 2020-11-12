package com.afonso.cursomc;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.domain.Cidade;
import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.Endereco;
import com.afonso.cursomc.domain.Estado;
import com.afonso.cursomc.domain.Produto;
import com.afonso.cursomc.domain.enums.TipoCliente;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.repositories.CidadeRepository;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.repositories.EnderecoRepository;
import com.afonso.cursomc.repositories.EstadoRepository;
import com.afonso.cursomc.repositories.ProdutoRepository;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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

        Estado oEstado1 = new Estado(null, "Minas Gerais");
        Estado oEstado2 = new Estado(null, "São Paulo");

        Cidade oCidade1 = new Cidade(null, "Uberlândia", oEstado1);
        Cidade oCidade2 = new Cidade(null, "São Paulo", oEstado2);
        Cidade oCidade3 = new Cidade(null, "Campinas", oEstado2);

        oEstado1.getCidades().addAll(Arrays.asList(oCidade1));
        oEstado2.getCidades().addAll(Arrays.asList(oCidade2, oCidade3));

        estadoRepository.saveAll(Arrays.asList(oEstado1, oEstado2));
        cidadeRepository.saveAll(Arrays.asList(oCidade1, oCidade2, oCidade3));

        Cliente oCli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36565656", TipoCliente.PESSOAFISICA);
        oCli1.getTelefones().addAll(Arrays.asList("33336", "555511"));

        Endereco oEnd1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "jardim", "38220834", oCli1, oCidade1);
        Endereco oEnd2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", oCli1, oCidade2);

        oCli1.getEnderecos().addAll(Arrays.asList(oEnd1, oEnd2));

        clienteRepository.saveAll(Arrays.asList(oCli1));
        enderecoRepository.saveAll(Arrays.asList(oEnd1, oEnd2));
    }
}
