package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.Gledalac;
import com.example.vebprojekat.entity.Korisnik;

import java.util.List;

public interface GledalacService {
    Gledalac create(Korisnik korisnik) throws Exception;

    Gledalac findOne(Long id);

    Gledalac update(Gledalac gledalac) throws Exception;

    void delete(Long id);

    List<Gledalac> findAll();

    Gledalac findByUsername(String username);
}
