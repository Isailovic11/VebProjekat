package com.example.vebprojekat.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Karta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer broj_mesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Projekcija projekcija;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Gledalac gledalac;

    public Karta(){}

    public Karta(Long id, Integer broj_mesta, Projekcija projekcija) {
        this.id = id;
        this.broj_mesta = broj_mesta;
        this.projekcija = projekcija;
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

    public Projekcija getProjekcija() {
        return projekcija;
    }

    public void setProjekcija(Projekcija projekcija) {
        this.projekcija = projekcija;
    }

    public Gledalac getGledalac() {
        return gledalac;
    }

    public void setGledalac(Gledalac gledalac) {
        this.gledalac = gledalac;
    }

    /*@Column
    private Long raspored_id;

    @Column
    private Long gledalac_id;

    @Column
    private Long sala_id;

    @Column
    private int broj_karata;

    public Karta() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRaspored_id() {
        return raspored_id;
    }

    public void setRaspored_id(Long raspored_id) {
        this.raspored_id = raspored_id;
    }

    public Long getGledalac_id() {
        return gledalac_id;
    }

    public void setGledalac_id(Long gledalac_id) {
        this.gledalac_id = gledalac_id;
    }

    public Long getSala_id() {
        return sala_id;
    }

    public void setSala_id(Long sala_id) {
        this.sala_id = sala_id;
    }

    public int getBroj_karata() {
        return broj_karata;
    }

    public void setBroj_karata(int broj_karata) {
        this.broj_karata = broj_karata;
    }*/
}
