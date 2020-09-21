package com.example.vebprojekat.service;

import com.example.vebprojekat.entity.Bioskop;
import com.example.vebprojekat.entity.Menadzer;

import java.util.List;
import java.util.Set;

public interface BioskopService {
    Bioskop create(Bioskop bioskop) throws Exception;

    Bioskop findOne(Long id);

    Bioskop update(Bioskop bioskop) throws Exception;

    void delete(Long id);

    List<Bioskop> findAll();

    Bioskop findByNaziv(String naziv);
}
