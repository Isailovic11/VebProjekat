package com.example.vebprojekat.entity.dto;

import com.example.vebprojekat.entity.OdgledanFilm;

public class OdgledanFilmDTO {
    private Long id;
    private String naziv_filma;
    private String gledalac_korisnicko;
    private Boolean ocenjen;
    private Integer ocena;

    public OdgledanFilmDTO(){}

    public OdgledanFilmDTO(OdgledanFilm f){
        this.id = f.getId();
        this.naziv_filma = f.getFilm().getNaziv();
        this.gledalac_korisnicko = f.getGledalac().getKorisnickoime();
        this.ocenjen = f.getOcenjen();
        this.ocena = f.getOcena();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv_filma() {
        return naziv_filma;
    }

    public void setNaziv_filma(String naziv_filma) {
        this.naziv_filma = naziv_filma;
    }

    public String getGledalac_korisnicko() {
        return gledalac_korisnicko;
    }

    public void setGledalac_korisnicko(String gledalac_korisnicko) {
        this.gledalac_korisnicko = gledalac_korisnicko;
    }

    public Boolean getOcenjen() {
        return ocenjen;
    }

    public void setOcenjen(Boolean ocenjen) {
        this.ocenjen = ocenjen;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
}
