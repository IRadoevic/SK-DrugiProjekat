package org.reservation.dto;


public class DecrementReservationCountDto {
    public DecrementReservationCountDto() {
    }

    public DecrementReservationCountDto(Long userId) {
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
