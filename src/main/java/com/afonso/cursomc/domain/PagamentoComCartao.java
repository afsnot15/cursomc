package com.afonso.cursomc.domain;

import com.afonso.cursomc.domain.enums.EstadoPagamento;

/**
 *
 * @author Afonso
 */
public class PagamentoComCartao extends Pagamento {

    private Integer numeroDeParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Integer numeroParcela) {
        this.numeroDeParcelas = numeroParcela;
    }

    public PagamentoComCartao(Integer numeroDeParcelas, Integer id, EstadoPagamento estado, Pedido pedido) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
    
    
}
