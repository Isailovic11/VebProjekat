package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Film;

public class FilmDTO {
    private Long id;
    private String naziv;
    private String opis;
    private String zanr;
    private Integer trajanje;
    private Double prosecnaocena;

    public FilmDTO() {}

    public FilmDTO(Long id, String naziv, String opis, String zanr, Integer trajanje, Double prosecnaocena) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.zanr = zanr;
        this.trajanje = trajanje;
        this.prosecnaocena = prosecnaocena;
    }

    public FilmDTO(Film film){
        this.id = film.getId();
        this.naziv = film.getNaziv();
        this.opis = film.getOpis();
        this.zanr = film.getZanr();
        this.trajanje = film.getTrajanje();
        this.prosecnaocena = film.getProsecnaocena();
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

    public void setProsecnaocena(Double prosecnaocena) {
        this.prosecnaocena = prosecnaocena;
    }
}
