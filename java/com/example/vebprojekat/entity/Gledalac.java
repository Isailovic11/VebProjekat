package com.example.vebprojekat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@DiscriminatorValue("Gledalac")
public class Gledalac extends Korisnik implements Serializable {

    @OneToMany(mappedBy = "gledalac", fetch = FetchType.EAGER)
    private Set<OdgledanFilm> odgledani_filmovi = new HashSet<>();

    @OneToMany(mappedBy = "gledalac", fetch = FetchType.EAGER)
    private Set<Karta> rezervisane_karte = new HashSet<>();

    @OneToMany(mappedBy = "gledalac", fetch = FetchType.EAGER)
    private Set<OcenjenFilm> ocenjeni_filmovi = new HashSet<>();

    public Gledalac(){}

    public Gledalac(Long id, String korisnickoime, String lozinka, String ime, String prezime, String kontakt_telefon, String email, Date datum_rodjenja, Uloga uloga, Boolean aktivan, Admin admin_approve, Set<OdgledanFilm> odgledani_filmovi, Set<Karta> rezervisane_karte, Set<OcenjenFilm> ocenjeni_filmovi) {
        super(id, korisnickoime, lozinka, ime, prezime, kontakt_telefon, email, datum_rodjenja, uloga, aktivan, admin_approve, true);
        this.odgledani_filmovi = odgledani_filmovi;
        this.rezervisane_karte = rezervisane_karte;
        this.ocenjeni_filmovi = ocenjeni_filmovi;
    }

    public Set<OdgledanFilm> getOdgledani_filmovi() {
        return odgledani_filmovi;
    }

    public void setOdgledani_filmovi(Set<OdgledanFilm> odgledani_filmovi) {
        this.odgledani_filmovi = odgledani_filmovi;
    }

    public Set<Karta> getRezervisane_karte() {
        return rezervisane_karte;
    }

    public void setRezervisane_karte(Set<Karta> rezervisane_karte) {
        this.rezervisane_karte = rezervisane_karte;
    }

    public Set<OcenjenFilm> getOcenjeni_filmovi() {
        return ocenjeni_filmovi;
    }

    public void setOcenjeni_filmovi(Set<OcenjenFilm> ocenjeni_filmovi) {
        this.ocenjeni_filmovi = ocenjeni_filmovi;
    }
}
