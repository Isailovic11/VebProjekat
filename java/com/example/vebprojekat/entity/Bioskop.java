package com.example.vebprojekat.entity;

import com.example.vebprojekat.entity.dto.BioskopDTO;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bioskop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String naziv;

    @Column
    private String adresa;

    @Column
    private String br_telefona;

    @Column
    private String email;

    @ManyToMany(mappedBy = "bioskopi" , fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Menadzer> menadzeri = new HashSet<Menadzer>();

    @OneToMany(mappedBy = "bioskop", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Sala> sale = new HashSet<Sala>();

    /*@OneToMany(mappedBy = "bioskop" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Projekcija> lista_projekcija = new HashSet<>();*/

    public Bioskop(){}

    public Bioskop(Long id){
        this.id = id;
    }

    public Bioskop(Long id, String naziv, String adresa, String br_telefona, String email, Set<Menadzer> menadzeri, Set<Sala> sale, Set<Projekcija> lista_projekcija) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.br_telefona = br_telefona;
        this.email = email;
        this.menadzeri = menadzeri;
        this.sale = sale;
        //this.lista_projekcija = lista_projekcija;
    }

    public Bioskop(BioskopDTO b){
        this.id = null;
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

    public Set<Menadzer> getMenadzeri() {
        return menadzeri;
    }

    public void setMenadzeri(Set<Menadzer> menadzeri) {
        this.menadzeri = menadzeri;
    }

    public Set<Sala> getSale() {
        return sale;
    }

    public void setSale(Set<Sala> sale) {
        this.sale = sale;
    }

    /*public Set<Projekcija> getLista_projekcija() {
        return lista_projekcija;
    }

    public void setLista_projekcija(Set<Projekcija> lista_projekcija) {
        this.lista_projekcija = lista_projekcija;
    }*/
}
