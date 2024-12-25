package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RezervacijaDto {
    private Long idStola;
    private  Long idKorisnika;
    private LocalDateTime dateTime;
}
