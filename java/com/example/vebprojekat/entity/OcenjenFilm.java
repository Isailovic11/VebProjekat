package com.example.vebprojekat.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OcenjenFilm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer ocena;

    /*@OneToOne(fetch = FetchType.EAGER)
    private OdgledanFilm odgledan_film;*/

    @ManyToOne(fetch = FetchType.EAGER)
    private Gledalac gledalac;

    @ManyToOne(fetch = FetchType.EAGER)
    private Film film;

    public OcenjenFilm() {}

    public OcenjenFilm(Long id, Integer ocena, OdgledanFilm odgledan_film, Gledalac gledalac, Film film) {
        this.id = id;
        this.ocena = ocena;
        //this.odgledan_film = odgledan_film;
        this.gledalac = gledalac;
        this.film = film;
    }

    public OcenjenFilm(OdgledanFilm of){
        this.gledalac = of.getGledalac();
        this.film = of.getFilm();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    /*public OdgledanFilm getOdgledan_film() {
        return odgledan_film;
    }

    public void setOdgledan_film(OdgledanFilm odgledan_film) {
        this.odgledan_film = odgledan_film;
    }*/

    public Gledalac getGledalac() {
        return gledalac;
    }

    public void setGledalac(Gledalac gledalac) {
        this.gledalac = gledalac;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}

