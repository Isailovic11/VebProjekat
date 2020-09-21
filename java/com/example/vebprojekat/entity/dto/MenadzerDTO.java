package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Menadzer;

public class MenadzerDTO {
    private Long id;
    private String korisnickoime;
    private String ime;
    private String prezime;
    private Integer br_bioskopa;
    private Boolean obrisan;

    public MenadzerDTO(){}

    public MenadzerDTO(Menadzer m){
        this.id = m.getId();
        this.korisnickoime = m.getKorisnickoime();
        this.ime = m.getIme();
        this.prezime = m.getPrezime();
        this.br_bioskopa = m.getBioskopi().size();
        this.obrisan = false;
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

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
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

    public Integer getBr_bioskopa() {
        return br_bioskopa;
    }

    public void setBr_bioskopa(Integer br_bioskopa) {
        this.br_bioskopa = br_bioskopa;
    }

    public Boolean getObrisan() {
        return obrisan;
    }

    public void setObrisan(Boolean obrisan) {
        this.obrisan = obrisan;
    }
}
