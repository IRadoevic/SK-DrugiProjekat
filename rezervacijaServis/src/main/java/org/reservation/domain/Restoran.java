package org.reservation.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Restoran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imeRestorana;
    private String adresa;
    private String opis;
    private String pocetakRadnogVremena;
    private String krajRadnogVremena;
    private Integer menagerId;
    private  String tipKuhinje;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeRestorana() {
        return imeRestorana;
    }

    public void setImeRestorana(String imeRestorana) {
        this.imeRestorana = imeRestorana;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getPocetakRadnogVremena() {
        return pocetakRadnogVremena;
    }

    public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
        this.pocetakRadnogVremena = pocetakRadnogVremena;
    }

    public String getKrajRadnogVremena() {
        return krajRadnogVremena;
    }

    public void setKrajRadnogVremena(String krajRadnogVremena) {
        this.krajRadnogVremena = krajRadnogVremena;
    }

    public Integer getMenagerId() {
        return menagerId;
    }

    public void setMenagerId(Integer menagerId) {
        this.menagerId = menagerId;
    }

    public String getTipKuhinje() {
        return tipKuhinje;
    }

    public void setTipKuhinje(String tipKuhinje) {
        this.tipKuhinje = tipKuhinje;
    }
}
