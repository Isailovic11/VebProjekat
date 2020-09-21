package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.Sala;

import java.util.List;

public interface SalaService {
    Sala create(Sala sala) throws Exception;

    Sala findOne(Long id);

    Sala update(Sala sala) throws Exception;

    void delete(Long id);

    List<Sala> findAll();

}
