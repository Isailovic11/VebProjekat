package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Karta;

public class KartaDTO {
    private Long id;
    private String film_naziv;
    private Integer ukupna_cena;
    private Integer broj_mesta;
    private String projekcija;
    private String sala;
    private String bioskop;

    public KartaDTO(){}

    public KartaDTO(Karta k){
        this.id = k.getId();
        this.broj_mesta = k.getBroj_mesta();
        this.film_naziv = k.getProjekcija().getFilm().getNaziv();
        this.ukupna_cena = k.getProjekcija().getCena() * broj_mesta;
        this.projekcija = new ProjekcijaDTO(k.getProjekcija()).getDatumvreme();
        this.sala = k.getProjekcija().getSala().getNaziv();
        this.bioskop = k.getProjekcija().getSala().getBioskop().getNaziv();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBroj_mesta() {
        return broj_mesta;
    }

    public void setBroj_mesta(Integer broj_mesta) {
        this.broj_mesta = broj_mesta;
    }

    public String getFilm_naziv() {
        return film_naziv;
    }

    public void setFilm_naziv(String film_naziv) {
        this.film_naziv = film_naziv;
    }

    public Integer getUkupna_cena() {
        return ukupna_cena;
    }

    public void setUkupna_cena(Integer ukupna_cena) {
        this.ukupna_cena = ukupna_cena;
    }

    public String getProjekcija() {
        return projekcija;
    }

    public void setProjekcija(String projekcija) {
        this.projekcija = projekcija;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getBioskop() {
        return bioskop;
    }

    public void setBioskop(String bioskop) {
        this.bioskop = bioskop;
    }

}
