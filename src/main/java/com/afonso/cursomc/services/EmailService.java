package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Afonso
 */
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pPedido);

    void sendEmail(SimpleMailMessage pMessage);
    
}
