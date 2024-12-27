package org.rag.listener.helper;

import org.rag.domain.Notification;
import org.rag.dto.PorukaDto;
import org.rag.service.NotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class NotificationListener {

    private MessageHelper messageHelper;
    private NotificationService emailService;

    public NotificationListener(MessageHelper messageHelper, NotificationService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        PorukaDto primljenPoruka = messageHelper.getMessage(message, PorukaDto.class);
        emailService.obradiPoruku(primljenPoruka);
    }
}
