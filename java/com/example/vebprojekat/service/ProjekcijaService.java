package com.example.vebprojekat.service;

import com.example.vebprojekat.entity.Projekcija;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjekcijaService {
    Projekcija create(Projekcija projekcija) throws Exception;

    Projekcija findOne(Long id);

    Projekcija update(Projekcija projekcija) throws Exception;

    void Delete(Long id);

    List<Projekcija> findAll();

    void save(Projekcija projekcija);

    List<Projekcija> findByDatumvreme(LocalDateTime datum_vreme);
}
