package org.rag.service;

import org.rag.domain.NotificationType;
import org.rag.dto.NotificationTypeDto;

import java.util.List;

public interface NotificationTypeService {
    void add(NotificationTypeDto notificationTypeDto);
    void delete(Long idNotifikacijeType);
    void azuriraj(NotificationTypeDto notificationTypeDto, Long id);
    List<NotificationType> izlistaj();
}
