package com.example.vebprojekat.repository;

import com.example.vebprojekat.entity.Projekcija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long> {
    public List<Projekcija> findByDatumvreme(LocalDateTime datum_vreme);
    public List<Projekcija> findByOrderByCenaAsc();
    public List<Projekcija> findByOrderByCenaDesc();
}
