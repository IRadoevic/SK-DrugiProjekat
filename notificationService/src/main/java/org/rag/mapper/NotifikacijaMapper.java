package org.rag.mapper;

import org.rag.domain.Notification;
import org.rag.dto.NotifikacijaDto;
import org.springframework.stereotype.Component;

@Component
public class NotifikacijaMapper {

    public NotifikacijaDto notificationToDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotifikacijaDto notifikacijaDto = new NotifikacijaDto();
        notifikacijaDto.setNotificationType(notification.getNotificationType());
        notifikacijaDto.setEmail(notification.getEmail());
        notifikacijaDto.setTekst(notification.getTekst());
        notifikacijaDto.setVremeSlanja(notification.getVremeSlanja());

        return notifikacijaDto;
    }

    public Notification dtoToNotification(NotifikacijaDto notifikacijaDto) {
        if (notifikacijaDto == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setNotificationType(notifikacijaDto.getNotificationType());
        notification.setEmail(notifikacijaDto.getEmail());
        notification.setTekst(notifikacijaDto.getTekst());
        notification.setVremeSlanja(notifikacijaDto.getVremeSlanja());
        return notification;
    }
}
