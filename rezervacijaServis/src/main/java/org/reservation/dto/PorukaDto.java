package org.reservation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


public class PorukaDto implements Serializable {
    private static final long serialVersionUID = 1L; //ovo vljd mora ako je serializable

    @NotBlank
    private String tipNotifikacije;
    private List<String> parametri;
    @Email
    private String email;

    public String getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(String tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }

    public List<String> getParametri() {
        return parametri;
    }

    public void setParametri(List<String> parametri) {
        this.parametri = parametri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
