package com.example.vebprojekat.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Projekcija implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Film film;

    @Column
    private Integer cena;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime datumvreme;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Sala sala;

    /*@ManyToMany(mappedBy = "rezervisane_karte", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Gledalac> gledaoci = new HashSet<Gledalac>();*/

    @OneToMany(mappedBy = "projekcija", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Karta> karte = new HashSet<>();

    public Projekcija(){}

    public Projekcija(Long id, Film film, Integer cena, LocalDateTime datumvreme, Sala sala, Set<Gledalac> gledaoci, Set<Karta> karte) {
        this.id = id;
        this.film = film;
        this.cena = cena;
        this.datumvreme = datumvreme;
        this.sala = sala;
        //this.gledaoci = gledaoci;
        this.karte = karte;
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

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public LocalDateTime getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(LocalDateTime datum_vreme) {
        this.datumvreme = datum_vreme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /*public Set<Gledalac> getGledaoci() {
        return gledaoci;
    }

    public void setGledaoci(Set<Gledalac> gledaoci) {
        this.gledaoci = gledaoci;
    }*/

    public Set<Karta> getKarte() {
        return karte;
    }

    public void setKarte(Set<Karta> karte) {
        this.karte = karte;
    }
}
