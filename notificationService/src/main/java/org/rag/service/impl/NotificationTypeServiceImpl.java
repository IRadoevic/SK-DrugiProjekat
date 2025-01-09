package org.rag.service.impl;

import org.rag.domain.NotificationType;
import org.rag.dto.NotificationTypeDto;
import org.rag.exeption.NotFoundException;
import org.rag.mapper.NotificationTypeMapper;
import org.rag.repository.NotificationTypeRepository;
import org.rag.service.NotificationTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationTypeServiceImpl implements NotificationTypeService {
    private  NotificationTypeRepository notificationTypeRepository;
   private NotificationTypeMapper notificationTypeMapper;

    public NotificationTypeServiceImpl(NotificationTypeRepository notificationTypeRepository, NotificationTypeMapper notificationTypeMapper) {
        this.notificationTypeRepository = notificationTypeRepository;
        this.notificationTypeMapper = notificationTypeMapper;
    }

    @Override
    public void add(NotificationTypeDto notificationTypeDto) {
    notificationTypeRepository.save(notificationTypeMapper.dtoToNotification(notificationTypeDto));
    }

    @Override
    public void delete(Long idNotifikacijeType) {
        notificationTypeRepository.deleteById(idNotifikacijeType);
    }

    @Override
    public void azuriraj(NotificationTypeDto notificationTypeDto, Long id) {
        NotificationType notificationType= notificationTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("NOtifikacija not found"));
        notificationType.setTekst(notificationTypeDto.getTekst());
        notificationType.setTip(notificationTypeDto.getTip());
        notificationTypeRepository.save(notificationType);
    }

    @Override
    public List<NotificationType> izlistaj() {
        return notificationTypeRepository.findAll();
    }

}
