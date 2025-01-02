package org.rag.domain;

import javax.persistence.*;
import java.util.List;


@Entity
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tip;
    // !!!!!!! poruka gde treba parametri da se ubace ima %, npr Pozdrav %, bla bla
    private String tekst;
    //private List<String> args;
    private int brArgumenata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getBrArgumenata() {
        return brArgumenata;
    }

    public void setBrArgumenata(int brArgumenata) {
        this.brArgumenata = brArgumenata;
    }
}
