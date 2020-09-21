package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.Film;

import java.util.List;

public interface FilmService {

    Film create(Film film) throws Exception;

    Film findOne(Long id);

    Film update(Film film) throws Exception;

    void delete(Long id);

    List<Film> findAll();

    Film findByNaziv(String naziv);
}
