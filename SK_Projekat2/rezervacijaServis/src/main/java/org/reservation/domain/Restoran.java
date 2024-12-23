package org.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@Entity
public class Restoran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imeRestorana;
    private String adresa;
    private String opis;
    private Integer brojStolova;
    private String pocetakRadnogVremena;
    private String krajRadnogVremena;
    private Integer menagerId;
    private  String tipKuhinje;

}
