package org.rag.controller;

import org.rag.domain.Notification;
import org.rag.dto.FilterNotificationDto;
import org.rag.security.CheckSecurity;
import org.rag.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationServiceImpl notificationService;

    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }


    @CheckSecurity(roles = {"admin"})
    @PostMapping("/filter/all")
    public List<Notification> getAllEmails(@RequestBody FilterNotificationDto filterNotificationDto) {
        return notificationService.sveEmailove(filterNotificationDto);
    }

    @CheckSecurity(roles = {"menadzr", "korisnik"})
    @PostMapping("/filter/user/{id}")
    public List<Notification> getUserEmails(@PathVariable Long id, @RequestBody FilterNotificationDto filterNotificationDto) {
        return notificationService.userEmailove(id, filterNotificationDto);
    }
}