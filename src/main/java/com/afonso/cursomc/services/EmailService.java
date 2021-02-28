package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.Pedido;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Afonso
 */
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pPedido);

    void sendEmail(SimpleMailMessage pMessage);
    
    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);
    
     void sendNewPasswordEmail(Cliente pCliente, String pNewPass);
}
