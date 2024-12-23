package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DostupnostDto {
    private Long id;
    private Long stoId;
    private LocalDateTime dateTime;
    private boolean available;
}
