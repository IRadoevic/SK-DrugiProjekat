package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoranDto {
    private Long id;
    private String imeRestorana;
    private String adresa;
    private String opis;
    private String pocetakRadnogVremena;
    private String krajRadnogVremena;
    private  String tipKuhinje;
    private  Integer managerId;

}
