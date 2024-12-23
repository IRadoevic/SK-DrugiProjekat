package com.raf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncrementReservationCountDto {

    public IncrementReservationCountDto() {
    }

    public IncrementReservationCountDto(Long userId) {
        this.userId = userId;
    }

    private Long userId;
}
