package org.rag.service;

import org.rag.dto.NotificationTypeDto;

public interface NotificationTypeService {
    void add(NotificationTypeDto notificationTypeDto);
    void delete(Long idNotifikacijeType);
    void azuriraj(NotificationTypeDto notificationTypeDto, Long id);
}
