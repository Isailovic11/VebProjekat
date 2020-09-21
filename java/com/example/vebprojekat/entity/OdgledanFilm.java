package com.example.vebprojekat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class OdgledanFilm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Film film;

    @ManyToOne(fetch = FetchType.EAGER )
    private Gledalac gledalac;

    @Column
    private Boolean ocenjen;

    @Column
    private Integer ocena;

    public OdgledanFilm(){}

    public OdgledanFilm(Long id, Film film, Gledalac gledalac, Boolean ocenjen, Integer ocena) {
        this.id = id;
        this.film = film;
        this.gledalac = gledalac;
        this.ocenjen = ocenjen;
        this.ocena = ocena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Gledalac getGledalac() {
        return gledalac;
    }

    public void setGledalac(Gledalac gledalac) {
        this.gledalac = gledalac;
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
    /*public OcenjenFilm getOcenjen_film() {
        return ocenjen_film;
    }

    public void setOcenjen_film(OcenjenFilm ocenjen_film) {
        this.ocenjen_film = ocenjen_film;
    }*/

}
