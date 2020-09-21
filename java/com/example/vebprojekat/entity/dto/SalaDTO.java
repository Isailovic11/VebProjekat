package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.Sala;

public class SalaDTO {
    private Long id;
    private Integer kapacitet;
    private String naziv;
    private String bioskop_naziv;

    public SalaDTO(){}

    public SalaDTO(Sala s){
        this.id = s.getId();
        this.kapacitet = s.getKapacitet();
        this.naziv = s.getNaziv();
        this.bioskop_naziv = s.getBioskop().getNaziv();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBioskop_naziv() {
        return bioskop_naziv;
    }

    public void setBioskop_naziv(String bioskop_naziv) {
        this.bioskop_naziv = bioskop_naziv;
    }
}
