package com.example.vebprojekat.service.IF;

import com.example.vebprojekat.entity.Karta;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KartaService {
    Karta create(Karta karta) throws Exception;

    Karta findOne(Long id);

    Karta update(Karta karta) throws Exception;

    void delete(Long id);

    List<Karta> findAll();

}
