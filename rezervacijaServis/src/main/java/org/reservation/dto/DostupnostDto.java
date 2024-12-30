package org.reservation.dto;


import java.time.LocalDateTime;

public class DostupnostDto {
    private Long stoId;
    private LocalDateTime dateTime;
    private boolean available;

    public Long getStoId() {
        return stoId;
    }

    public void setStoId(Long stoId) {
        this.stoId = stoId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
