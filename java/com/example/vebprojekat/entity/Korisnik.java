package com.example.vebprojekat.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected String korisnickoime;

    @Column
    protected String lozinka;

    @Column
    protected String ime;

    @Column
    protected String prezime;

    @Column
    protected String kontakt_telefon;

    @Column
    protected String email;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date datum_rodjenja;

    @Column
    protected Uloga uloga;

    @Column
    protected Boolean aktivan;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Admin admin_approve;

    @Column
    private Boolean approved;


    public Korisnik(){}

    public Korisnik(Long id, String korisnickoime, String lozinka, String ime, String prezime, String kontakt_telefon, String email, Date datum_rodjenja, Uloga uloga, Boolean aktivan, Admin admin_approve, Boolean approved) {
        this.id = id;
        this.korisnickoime = korisnickoime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt_telefon = kontakt_telefon;
        this.email = email;
        this.datum_rodjenja = datum_rodjenja;
        this.uloga = uloga;
        this.aktivan = aktivan;
        this.admin_approve = admin_approve;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String username) {
        this.korisnickoime = username;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String password) {
        this.lozinka = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String name) {
        this.ime = name;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String surname) {
        this.prezime = surname;
    }

    public String getKontakt_telefon() {
        return kontakt_telefon;
    }

    public void setKontakt_telefon(String phone_number) {
        this.kontakt_telefon = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(Date birth_date) {
        this.datum_rodjenja = birth_date;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga role) {
        this.uloga = role;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Admin getAdmin_approve() {
        return admin_approve;
    }

    public void setAdmin_approve(Admin admin_approve) {
        this.admin_approve = admin_approve;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", korisnickoime='" + korisnickoime + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", uloga=" + uloga +
                '}';
    }
}
