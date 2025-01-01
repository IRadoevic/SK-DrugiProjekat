package org.reservation.dto;

import java.time.LocalDateTime;
import org.reservation.domain.Sto; // Import the Sto class

public class DostupnostDto {
    private Sto sto;
    private LocalDateTime dateTime;
    private boolean available;
    private int brojOsoba;

    // Getters and setters for the Sto field
    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    // Getters and setters for the other fields
    public int getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(int brojOsoba) {
        this.brojOsoba = brojOsoba;
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
