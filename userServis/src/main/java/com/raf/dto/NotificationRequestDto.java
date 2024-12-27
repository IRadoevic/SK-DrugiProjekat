package com.raf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String notificationType;
}
