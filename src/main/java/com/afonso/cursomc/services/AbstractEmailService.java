package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Pedido;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Afonso
 */
public abstract class AbstractEmailService implements EmailService{
    
    @Value("${default.sender}")
    private String sender;
    
    @Override
    public void sendOrderConfirmationEmail(Pedido pPedido){
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pPedido);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pPedido) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(pPedido.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido Confirmado! Código: " + pPedido.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(pPedido.toString());
        return sm;
    }
}
