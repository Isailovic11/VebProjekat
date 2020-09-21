package com.example.vebprojekat.repository;

import com.example.vebprojekat.entity.Menadzer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenadzerRepository extends JpaRepository<Menadzer, Long> {
    public Menadzer findByKorisnickoime(String username);

}
