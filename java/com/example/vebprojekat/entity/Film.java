package com.example.vebprojekat.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String naziv;

    @Column
    private String opis;

    @Column
    private String zanr;

    @Column
    private Integer trajanje;

    @Column
    private Double prosecnaocena;

    @Column
    private Integer brojocenjivaca;

    @Column
    private Integer zbirocena;

    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    private Set<OdgledanFilm> odgledanifilmovi;


    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Projekcija> terminske_liste = new HashSet<>();


    public Film(){}

    public Film(Long id, String naziv, String opis, String zanr, Integer trajanje, Double prosecnaocena, Integer brojocenjivaca, Integer zbirocena, Set<OdgledanFilm> odgledanifilmovi, Set<Projekcija> terminske_liste) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.zanr = zanr;
        this.trajanje = trajanje;
        this.prosecnaocena = prosecnaocena;
        this.brojocenjivaca = brojocenjivaca;
        this.zbirocena = zbirocena;
        this.odgledanifilmovi = odgledanifilmovi;
        this.terminske_liste = terminske_liste;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public Integer getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Integer trajanje) {
        this.trajanje = trajanje;
    }

    public Double getProsecnaocena() {
        return prosecnaocena;
    }

    public void setProsecnaocena(Double prosecna_ocena) {
        this.prosecnaocena = prosecna_ocena;
    }

    public Integer getBrojocenjivaca() {
        return brojocenjivaca;
    }

    public void setBrojocenjivaca(Integer brojocenjivaca) {
        this.brojocenjivaca = brojocenjivaca;
    }

    public Integer getZbirocena() {
        return zbirocena;
    }

    public void setZbirocena(Integer zbirocena) {
        this.zbirocena = zbirocena;
    }

    public Set<OdgledanFilm> getOdgledanifilmovi() {
        return odgledanifilmovi;
    }

    public void setOdgledanifilmovi(Set<OdgledanFilm> odgledanifilmovi) {
        this.odgledanifilmovi = odgledanifilmovi;
    }

    public Set<Projekcija> getTerminske_liste() {
        return terminske_liste;
    }

    public void setTerminske_liste(Set<Projekcija> terminske_liste) {
        this.terminske_liste = terminske_liste;
    }

    public void ocenjivaciIncrement(){
        this.brojocenjivaca++;
    }

    public void noviZbir(Integer dodatak){
        this.zbirocena += dodatak;
    }

    public void racunajProsek(){
        DecimalFormat numberFormat = new DecimalFormat("#.##");
        this.prosecnaocena = Double.parseDouble(numberFormat.format(((double)this.zbirocena) / this.brojocenjivaca));
    }
}