package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Gledalac;

public class GledalacDTO {
    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoime;
    private String email;
    private Integer broj_odgledanih;

    public GledalacDTO(){}

    public GledalacDTO(Gledalac g){
        this.id = g.getId();
        this.ime = g.getIme();
        this.prezime = g.getPrezime();
        this.korisnickoime = g.getKorisnickoime();
        this.email = g.getEmail();
        this.broj_odgledanih = g.getOdgledani_filmovi().size() + g.getOcenjeni_filmovi().size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBroj_odgledanih() {
        return broj_odgledanih;
    }

    public void setBroj_odgledanih(Integer broj_odgledanih) {
        this.broj_odgledanih = broj_odgledanih;
    }
}
