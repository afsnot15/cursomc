package com.afonso.cursomc;

import com.afonso.cursomc.domain.Categoria;
import com.afonso.cursomc.domain.Cidade;
import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.Endereco;
import com.afonso.cursomc.domain.Estado;
import com.afonso.cursomc.domain.ItemPedido;
import com.afonso.cursomc.domain.Pagamento;
import com.afonso.cursomc.domain.PagamentoComBoleto;
import com.afonso.cursomc.domain.PagamentoComCartao;
import com.afonso.cursomc.domain.Pedido;
import com.afonso.cursomc.domain.Produto;
import com.afonso.cursomc.domain.enums.EstadoPagamento;
import com.afonso.cursomc.domain.enums.TipoCliente;
import com.afonso.cursomc.repositories.CategoriaRepository;
import com.afonso.cursomc.repositories.CidadeRepository;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.repositories.EnderecoRepository;
import com.afonso.cursomc.repositories.EstadoRepository;
import com.afonso.cursomc.repositories.ItemPedidoRepository;
import com.afonso.cursomc.repositories.PagamentoRepository;
import com.afonso.cursomc.repositories.PedidoRepository;
import com.afonso.cursomc.repositories.ProdutoRepository;
import java.text.SimpleDateFormat;
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

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository ItemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Categoria> vCategoria = new ArrayList<>();
        ArrayList<Produto> vProduto = new ArrayList<>();

        Produto oProduto1 = new Produto();
        oProduto1.setId(null);
        oProduto1.setDescricao("Computador");
        oProduto1.setPreco(2000.00);

        vProduto.add(oProduto1);

        Produto oProduto2 = new Produto();
        oProduto2.setId(null);
        oProduto2.setDescricao("Impressora");
        oProduto2.setPreco(800.00);

        vProduto.add(oProduto2);

        Produto oProduto3 = new Produto();
        oProduto3.setId(null);
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

        Categoria oCategoria3 = new Categoria();
        oCategoria3.setId(null);
        oCategoria3.setNome("Cama mesa e banho");

        vCategoria.add(oCategoria3);

        Categoria oCategoria4 = new Categoria();
        oCategoria4.setId(null);
        oCategoria4.setNome("Eletronicos");

        vCategoria.add(oCategoria4);

        Categoria oCategoria5 = new Categoria();
        oCategoria5.setId(null);
        oCategoria5.setNome("Jardinagem");

        vCategoria.add(oCategoria5);

        Categoria oCategoria6 = new Categoria();
        oCategoria6.setId(null);
        oCategoria6.setNome("Decoracao");

        vCategoria.add(oCategoria6);

        Categoria oCategoria7 = new Categoria();
        oCategoria7.setId(null);
        oCategoria7.setNome("Perfumaria");

        vCategoria.add(oCategoria7);

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), oCli1, oEnd1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/20/2017 10:32"), oCli1, oEnd2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        oCli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, oProduto1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, oProduto3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, oProduto2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        oProduto1.getItens().addAll(Arrays.asList(ip1));
        oProduto2.getItens().addAll(Arrays.asList(ip3));
        oProduto3.getItens().addAll(Arrays.asList(ip2));

        ItemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

    }
}
