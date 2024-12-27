package org.rag.mapper;

import org.rag.domain.NotificationType;
import org.rag.dto.NotificationTypeDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationTypeMapper {
    public  NotificationTypeDto notificationtoDto(NotificationType notificationType) {
        if (notificationType == null) {
            return null;
        }

        NotificationTypeDto dto = new NotificationTypeDto();
        dto.setTip(notificationType.getTip());
        dto.setTekst(notificationType.getTekst());

        return dto;
    }

    public  NotificationType dtoToNotification(NotificationTypeDto notificationTypeDto) {
        if (notificationTypeDto == null) {
            return null;
        }

        NotificationType entity = new NotificationType();
        entity.setTip(notificationTypeDto.getTip());
        entity.setTekst(notificationTypeDto.getTekst());

        return entity;
    }
}
