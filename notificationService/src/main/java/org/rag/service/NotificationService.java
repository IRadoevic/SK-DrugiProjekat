package org.rag.service;

import org.rag.domain.Notification;
import org.rag.dto.FilterNotificationDto;
import org.rag.dto.NotifikacijaDto;
import org.rag.dto.PorukaDto;

import java.util.List;

public interface NotificationService {
    public void sendEmail(String to, String subject, String text);
    public void obradiPoruku(PorukaDto  porukaDto);
    public void dodajNotifikaciju(NotifikacijaDto notifikacijaDto);
    List<Notification> sveEmailove(FilterNotificationDto filterNotificationDto);
    List<Notification> userEmailove(Long id, FilterNotificationDto filterNotificationDto);


}
