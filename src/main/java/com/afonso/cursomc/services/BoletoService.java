package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.PagamentoComBoleto;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Afonso
 */
@Service
public class BoletoService {
    
    public void preencherPagamentoComBoleto(PagamentoComBoleto pPagamento, Date pInstantePedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pInstantePedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pPagamento.setDataVencimento(cal.getTime());
    }
}
