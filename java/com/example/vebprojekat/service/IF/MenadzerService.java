package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.Korisnik;
import com.example.vebprojekat.entity.Menadzer;

import java.util.List;

public interface MenadzerService {
    Menadzer create(Korisnik korisnik) throws Exception;

    Menadzer findOne(Long id);

    Menadzer update(Menadzer menadzer) throws Exception;

    void delete(Long id);

    List<Menadzer> findAll();

    Menadzer findByUsername(String username);

    Menadzer save(Menadzer menadzer);
}
