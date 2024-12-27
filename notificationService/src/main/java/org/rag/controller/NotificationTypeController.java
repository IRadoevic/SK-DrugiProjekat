package org.rag.controller;

import org.rag.dto.NotificationTypeDto;
import org.rag.security.CheckSecurity;
import org.rag.service.NotificationTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificationTypes")
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    public NotificationTypeController(NotificationTypeService notificationTypeService) {
        this.notificationTypeService = notificationTypeService;
    }

    @CheckSecurity(roles = {"admin"})
    @PostMapping
    public ResponseEntity<Void> addNotificationType(@RequestBody NotificationTypeDto notificationTypeDto) {
        notificationTypeService.add(notificationTypeDto);
        return ResponseEntity.ok().build();
    }

    @CheckSecurity(roles = {"admin"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id) {
        notificationTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @CheckSecurity(roles = {"admin"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotificationType(@RequestBody NotificationTypeDto notificationTypeDto, @PathVariable Long id) {
        notificationTypeService.azuriraj(notificationTypeDto, id);
        return ResponseEntity.ok().build();
    }

}
