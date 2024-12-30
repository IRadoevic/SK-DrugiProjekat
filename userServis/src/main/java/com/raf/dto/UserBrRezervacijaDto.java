package com.raf.dto;
/*
import lombok.Getter;
import lombok.Setter;

/*
@Getter
@Setter*/
public class UserBrRezervacijaDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRezervacija(Integer rezervacija) {
        this.rezervacija = rezervacija;
    }

    public Integer getRezervacija() {
        return rezervacija;
    }

    private  Integer rezervacija;
}
