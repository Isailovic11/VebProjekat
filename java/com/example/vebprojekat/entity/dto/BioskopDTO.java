package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Bioskop;
import com.example.vebprojekat.entity.Menadzer;

public class BioskopDTO {
    private Long id;
    private String naziv;
    private String adresa;
    private String br_telefona;
    private String email;
    private String inicijalni_menadzer;

    public BioskopDTO(){}

    public BioskopDTO(Bioskop b){
        this.id = b.getId();
        this.naziv = b.getNaziv();
        this.adresa = b.getAdresa();
        this.br_telefona = b.getBr_telefona();
        this.email = b.getEmail();
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBr_telefona() {
        return br_telefona;
    }

    public void setBr_telefona(String br_telefona) {
        this.br_telefona = br_telefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInicijalni_menadzer() {
        return inicijalni_menadzer;
    }

    public void setInicijalni_menadzer(String inicijalni_menadzer) {
        this.inicijalni_menadzer = inicijalni_menadzer;
    }
}
