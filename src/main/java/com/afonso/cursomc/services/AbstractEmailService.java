package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.Pedido;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author Afonso
 */
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pPedido) {
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

    protected String htmlFromTemplatePedido(Pedido pPedido) {
        Context context = new Context();
        context.setVariable("pedido", pPedido);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido pPedido) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(pPedido);
            sendHtmlEmail(mm);
        } catch (MessagingException ex) {
            sendOrderConfirmationEmail(pPedido);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido pPedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(pPedido.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido Confirmado, código: " + pPedido.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(pPedido), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(Cliente pCliente, String pNewPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(pCliente, pNewPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente pCliente, String pNewPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(pCliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + pNewPass);
        return sm;
    }
}
