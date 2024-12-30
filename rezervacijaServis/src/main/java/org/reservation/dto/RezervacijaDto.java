package org.reservation.dto;


import java.time.LocalDateTime;

public class RezervacijaDto {
    private Long idStola;
    private  Long idKorisnika;
    private LocalDateTime dateTime;

    public Long getIdStola() {
        return idStola;
    }

    public void setIdStola(Long idStola) {
        this.idStola = idStola;
    }

    public Long getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Long idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
