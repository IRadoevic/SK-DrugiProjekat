package org.rag.controller;

import org.rag.domain.NotificationType;
import org.rag.dto.NotificationTypeDto;
import org.rag.security.CheckSecurity;
import org.rag.service.NotificationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificationTypes")
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    public NotificationTypeController(NotificationTypeService notificationTypeService) {
        this.notificationTypeService = notificationTypeService;
    }

    @CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/add")
    public ResponseEntity<Void> addNotificationType(@RequestBody NotificationTypeDto notificationTypeDto,
                                                    @RequestHeader("Authorization") String authorization) {
        notificationTypeService.add(notificationTypeDto);
        return ResponseEntity.ok().build();
    }

    @CheckSecurity(roles = {"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id,
                                                       @RequestHeader("Authorization") String authorization) {
        notificationTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @CheckSecurity(roles = {"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotificationType(@RequestHeader("Authorization") String authorization,
                                                       @RequestBody NotificationTypeDto notificationTypeDto, @PathVariable Long id) {
        notificationTypeService.azuriraj(notificationTypeDto, id);
        return ResponseEntity.ok().build();
    }

    @CheckSecurity(roles = {"ADMIN"})
    @GetMapping("/list")
    public ResponseEntity<List<NotificationType>> listNotificationTypes(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(notificationTypeService.izlistaj(), HttpStatus.OK);
    }

}
