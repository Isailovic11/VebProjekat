package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Karta;
import com.example.vebprojekat.entity.Projekcija;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ProjekcijaDTO implements Serializable {
    private Long id;
    private String naziv;
    private String opis;
    private String zanr;
    private Integer trajanje;
    private Double prosecnaocena;
    private Integer cena;
    private String datumvreme;
    private String sala;
    private Integer broj_preostalih_mesta;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime datumvreme_format;
    private String naziv_bioskopa;

    public ProjekcijaDTO(){}

    public ProjekcijaDTO(Long id, String naziv, String opis, String zanr, Integer trajanje, Double prosecnaocena, Integer cena, String datumvreme, String sala, Integer broj_preostalih_mesta, LocalDateTime datumvreme_format, String naziv_bioskopa) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.zanr = zanr;
        this.trajanje = trajanje;
        this.prosecnaocena = prosecnaocena;
        this.cena = cena;
        this.datumvreme = datumvreme;
        this.sala = sala;
        this.broj_preostalih_mesta = broj_preostalih_mesta;
        this.datumvreme_format = datumvreme_format;
        this.naziv_bioskopa = naziv_bioskopa;
    }

    public ProjekcijaDTO(Projekcija p){
        this.id = p.getId();
        this.naziv = p.getFilm().getNaziv();
        this.opis = p.getFilm().getOpis();
        this.zanr = p.getFilm().getZanr();
        this.trajanje = p.getFilm().getTrajanje();
        this.prosecnaocena = p.getFilm().getProsecnaocena();
        this.cena = p.getCena();
        this.datumvreme = "";
        String temp = p.getDatumvreme().toString();
        String[] strings = temp.split("T");
        for(String s: strings){
            this.datumvreme += s;
            this.datumvreme += " ";
        }
        this.datumvreme = this.datumvreme.substring(0, this.datumvreme.length() - 1);

        this.sala = p.getSala().getNaziv();

        Integer broj_rezervisanih_mesta = 0;
        for(Karta k: p.getKarte()){
            broj_rezervisanih_mesta += k.getBroj_mesta();
        }

        this.broj_preostalih_mesta = p.getSala().getKapacitet() - broj_rezervisanih_mesta;
        this.naziv_bioskopa = p.getSala().getBioskop().getNaziv();
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

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(String datumvreme) {
        this.datumvreme = datumvreme;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Integer getBroj_preostalih_mesta() {
        return broj_preostalih_mesta;
    }

    public void setBroj_preostalih_mesta(Integer broj_preostalih_mesta) {
        this.broj_preostalih_mesta = broj_preostalih_mesta;
    }

    public LocalDateTime getDatumvreme_format() {
        return datumvreme_format;
    }

    public void setDatumvreme_format(LocalDateTime datumvreme_format) {
        this.datumvreme_format = datumvreme_format;
    }

    public String getNaziv_bioskopa() {
        return naziv_bioskopa;
    }

    public void setNaziv_bioskopa(String naziv_bioskopa) {
        this.naziv_bioskopa = naziv_bioskopa;
    }
}
