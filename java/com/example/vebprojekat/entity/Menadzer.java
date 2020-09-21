package com.example.vebprojekat.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Menadzer")
public class Menadzer extends Korisnik {

    @ManyToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Bioskop> bioskopi = new HashSet<>();

    public Menadzer(){};

    public Menadzer(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTel, String email, Date datum, Uloga uloga, Boolean aktivan, Admin admin_approve, Boolean approved, Set<Bioskop> bioskopi) {
        super(id, korisnickoIme, lozinka, ime, prezime, kontaktTel, email, datum, uloga, aktivan, admin_approve, approved);
        this.bioskopi = bioskopi;
    }

    public Set<Bioskop> getBioskopi() {
        return bioskopi;
    }

    public void setBioskopi(Set<Bioskop> bioskopi) {
        this.bioskopi = bioskopi;
    }

}
