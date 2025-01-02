package com.raf.listener;

import com.raf.dto.DecrementReservationCountDto;
import com.raf.service.impl.UserService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class DecrementReservationCountListener {
    private MessageHelper messageHelper;
    private UserService userService;

    public DecrementReservationCountListener(MessageHelper messageHelper, UserService userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    @JmsListener(destination = "${destination.decrementReservationCount}", concurrency = "5-10")
    public void decrementReservationCount(Message message) throws JMSException {
        DecrementReservationCountDto decrementReservationCountDto = messageHelper.getMessage(message, DecrementReservationCountDto.class);
        System.out.println(decrementReservationCountDto);
        userService.decrementReservationCount(decrementReservationCountDto);
    }

}
