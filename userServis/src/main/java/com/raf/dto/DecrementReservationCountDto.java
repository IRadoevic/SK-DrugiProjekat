package com.raf.dto;
/*
import lombok.Getter;
import lombok.Setter;*/
/*
@Getter
@Setter*/
public class DecrementReservationCountDto {
    public DecrementReservationCountDto() {
    }

    public DecrementReservationCountDto(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
