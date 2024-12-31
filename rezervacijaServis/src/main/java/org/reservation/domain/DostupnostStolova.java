package org.reservation.domain;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class DostupnostStolova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private Sto sto;

    private LocalDateTime datumVreme;
    //ovo zameniit sa vrednosti brojMesta iz sto
    private boolean dostupnostStolova;
    private String lokacija;
    private String tipKuhinje;
    private Long userId;

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }


    public boolean isDostupnostStolova() {
        return dostupnostStolova;
    }

    public void setDostupnostStolova(boolean dostupnostStolova) {
        this.dostupnostStolova = dostupnostStolova;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getTipKuhinje() {
        return tipKuhinje;
    }

    public void setTipKuhinje(String tipKuhinje) {
        this.tipKuhinje = tipKuhinje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
