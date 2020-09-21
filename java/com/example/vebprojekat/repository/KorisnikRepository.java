package com.example.vebprojekat.repository;

import com.example.vebprojekat.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    public Korisnik findByKorisnickoime(String username);
}
