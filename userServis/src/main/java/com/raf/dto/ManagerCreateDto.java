package com.raf.dto;
/*
import lombok.Data;
import lombok.Getter;
import lombok.Setter;*/
import org.h2.api.DatabaseEventListener;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/*
@Getter
@Setter*/
public class ManagerCreateDto {
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @Length(min = 8, max = 20)
    private String password;
    //@NotBlank
    private String role;
    @NotNull
    private LocalDate datumRodjenja;
    @NotNull
    private LocalDate datumZaposljavanja;
    @NotBlank
    private String  nazivrestorana;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setDatumZaposljavanja(LocalDate datumZaposljavanja) {
        this.datumZaposljavanja = datumZaposljavanja;
    }

    public void setNazivrestorana(String nazivrestorana) {
        this.nazivrestorana = nazivrestorana;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public LocalDate getDatumZaposljavanja() {
        return datumZaposljavanja;
    }

    public String getNazivrestorana() {
        return nazivrestorana;
    }
}
