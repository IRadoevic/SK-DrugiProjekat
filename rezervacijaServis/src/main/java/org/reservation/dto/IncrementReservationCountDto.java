package org.reservation.dto;


public class IncrementReservationCountDto {

    public IncrementReservationCountDto() {
    }

    public IncrementReservationCountDto(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
