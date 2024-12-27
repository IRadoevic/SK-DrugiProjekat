package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpateDostupnostDto {
    private LocalDateTime dateTime;
    private boolean available;
}
