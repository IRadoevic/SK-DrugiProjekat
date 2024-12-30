package org.reservation.dto;


public class UserBrRezervacijaDto {
    private Long id;
    private  Integer rezervacija;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Integer rezervacija) {
        this.rezervacija = rezervacija;
    }
}
