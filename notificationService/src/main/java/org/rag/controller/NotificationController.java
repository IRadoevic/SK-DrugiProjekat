package org.rag.controller;

import org.rag.domain.Notification;
import org.rag.dto.FilterNotificationDto;
import org.rag.security.CheckSecurity;
import org.rag.security.service.TokenService;
import org.rag.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationServiceImpl notificationService;
    private TokenService tokenService;

    public NotificationController(NotificationServiceImpl notificationService, TokenService tokenService) {
        this.notificationService = notificationService;
        this.tokenService = tokenService;
    }


    @CheckSecurity(roles = {"ADMIN"})
    @GetMapping("/filter/all")
    public ResponseEntity<List<Notification>> getAllEmails(@RequestHeader("Authorization") String authorization,
                                                           @RequestParam(required = false) String tip,
                                                           @RequestParam(required = false) String email,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               LocalDateTime startDate,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               LocalDateTime endDate) {
        FilterNotificationDto filter = new FilterNotificationDto();
        filter.setEmail(email);
        filter.setTip(tip);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        return new ResponseEntity<>(notificationService.sveEmailove(filter), HttpStatus.OK);
    }


    @CheckSecurity(roles = {"MANAGER", "USER", "ADMIN"})
    @GetMapping("/filter/user")
    public ResponseEntity<List<Notification>> getUserEmails(@RequestHeader("Authorization") String authorization,
                                            @RequestParam(required = false) String tip,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime endDate) {
        FilterNotificationDto filter = new FilterNotificationDto();
        filter.setTip(tip);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        String aut = authorization.split(" ")[1];
        int id = tokenService.getUserIdFromToken(aut);
        String em = tokenService.getUserEmailFromToken(aut);
        filter.setEmail(em);
        return new ResponseEntity<>(notificationService.userEmailove((long) id, filter), HttpStatus.OK);
    }
}