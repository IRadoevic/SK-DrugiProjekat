package org.reservation.dto;


import java.time.LocalDateTime;

public class UpateDostupnostDto {
    private LocalDateTime dateTime;
    private boolean available;

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
