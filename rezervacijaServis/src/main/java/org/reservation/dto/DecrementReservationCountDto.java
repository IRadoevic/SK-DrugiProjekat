package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecrementReservationCountDto {
    public DecrementReservationCountDto() {
    }

    public DecrementReservationCountDto(Long userId) {
        this.userId = userId;
    }

    private Long userId;
}
