package com.afonso.cursomc.services;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author afonso.gomes
 */
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage pMessage) {
        LOG.info("Enviando envio de email...");
        mailSender.send(pMessage);
        LOG.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage pMessage) {
        LOG.info("Enviando envio de email...");
        javaMailSender.send(pMessage);
        LOG.info("Email enviado");
    }
}
