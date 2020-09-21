package com.example.vebprojekat.repository;

import com.example.vebprojekat.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
    public Film findByNaziv(String naziv);
}
