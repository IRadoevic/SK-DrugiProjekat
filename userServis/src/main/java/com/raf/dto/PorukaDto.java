package com.raf.dto;
/*
import lombok.Getter;
import lombok.Setter;*/

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

/*
@Getter
@Setter*/
public class PorukaDto {
    @NotBlank
    private String tipNotifikacije;

    public String getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(String tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }

    public void setParametri(List<String> parametri) {
        this.parametri = parametri;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getParametri() {
        return parametri;
    }

    public String getEmail() {
        return email;
    }

    private List<String> parametri;
    @Email
    private String email;
}
