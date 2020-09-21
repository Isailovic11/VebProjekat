package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.OdgledanFilm;

import java.util.List;

public interface OdgledanFilmService {
    OdgledanFilm create(OdgledanFilm odgledanFilm);

    OdgledanFilm findOne(Long id);

    OdgledanFilm update(OdgledanFilm odgledanFilm) throws Exception;

    void delete(Long id);

    List<OdgledanFilm> findAll();
}
