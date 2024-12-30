package com.raf.domain;
/*
import lombok.Getter;
import lombok.Setter;
*/
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

//@Getter
//@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate datumRodjenja;
    private LocalDate datumZaposljavanja;
    private Boolean banned = false;
    private Integer brojRezervacija = 0;
    private  String role;
    private  String nazivRestorana;

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setDatumZaposljavanja(LocalDate datumZaposljavanja) {
        this.datumZaposljavanja = datumZaposljavanja;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void setBrojRezervacija(Integer brojRezervacija) {
        this.brojRezervacija = brojRezervacija;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setNazivRestorana(String nazivRestorana) {
        this.nazivRestorana = nazivRestorana;
    }

    public Long getId() {
        return id;
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

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public LocalDate getDatumZaposljavanja() {
        return datumZaposljavanja;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getBrojRezervacija() {
        return brojRezervacija;
    }

    public String getRole() {
        return role;
    }

    public String getNazivRestorana() {
        return nazivRestorana;
    }
}
