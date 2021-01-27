package com.afonso.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author afonso.gomes
 */
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage pMessage) {
        LOG.info("Enviando envio de email...");
        mailSender.send(pMessage);
        LOG.info("Email enviado");
    }
}
